'use strict';

const $ = id => document.getElementById(id);
const state = {section: 'languages', lists: {}, availability: null, pages: {}, renderToken: 0};
const field = (key, label, type = 'text', extra = {}) => ({key, label, type, ...extra});
const sections = {
  languages: {
    title: 'Diller', url: '/languages',
    columns: [['id', 'ID'], ['languageName', 'Dil']],
    fields: [field('languageName', 'Dil adı')]
  },
  faculties: {
    title: 'Fakülteler', url: '/faculties',
    columns: [['facultyId', 'ID'], ['facultyName', 'Fakülte']],
    fields: [field('facultyName', 'Fakülte adı')]
  },
  departments: {
    title: 'Bölümler', url: '/departments', requires: ['languages', 'faculties'],
    columns: [['id', 'ID'], ['departmentName', 'Bölüm'], ['departmentCode', 'Kod'], ['faculty.facultyName', 'Fakülte'], ['language.languageName', 'Dil']],
    fields: [field('departmentName', 'Bölüm adı'), field('departmentCode', 'Kod'), field('faculty.facultyId', 'Fakülte', 'faculties'), field('language.id', 'Dil', 'languages')]
  },
  lecturers: {
    title: 'Öğretmenler', url: '/lecturers', requires: ['departments'], register: 'LECTURER',
    columns: [['firstName', 'Ad'], ['lastName', 'Soyad'], ['department.departmentName', 'Bölüm'], ['userId', 'ID']]
  },
  students: {
    title: 'Öğrenciler', url: '/students', requires: ['departments'], register: 'STUDENT',
    columns: [['firstName', 'Ad'], ['lastName', 'Soyad'], ['studentNumber', 'Numara'], ['department.departmentName', 'Bölüm'], ['userId', 'ID']]
  },
  courses: {
    title: 'Dersler', url: '/courses', requires: ['languages', 'departments'],
    columns: [['courseCode', 'Kod'], ['courseName', 'Ders'], ['department.departmentName', 'Bölüm'], ['courseId', 'ID']],
    fields: [field('courseName', 'Ders adı'), field('courseCode', 'Kod'), field('courseEcts', 'AKTS', 'number', {step: '0.1'}), field('courseCredit', 'Kredi', 'number', {step: '0.1'}), field('hoursTheoretical', 'Teorik saat', 'number'), field('hoursPractical', 'Uygulama saati', 'number'), field('minAttendancePercent', 'Asgari devam %', 'number'), field('language.id', 'Dil', 'languages'), field('department.id', 'Bölüm', 'departments'), field('online', 'Çevrimiçi', 'boolean'), field('elective', 'Seçmeli', 'boolean')]
  },
  lecturerCourses: {
    title: 'Öğretmen–ders', url: '/lecturer-courses', requires: ['lecturers', 'courses'],
    columns: [['lecturer.firstName', 'Öğretmen'], ['lecturer.lastName', 'Soyad'], ['course.courseCode', 'Ders'], ['active', 'Aktif']],
    fields: [field('lecturer.userId', 'Öğretmen', 'lecturers'), field('course.courseId', 'Ders', 'courses'), field('active', 'Aktif', 'boolean')]
  },
  studentCourses: {
    title: 'Öğrenci–ders', url: '/student-courses', requires: ['students', 'courses'],
    columns: [['student.firstName', 'Öğrenci'], ['student.lastName', 'Soyad'], ['course.courseCode', 'Ders'], ['active', 'Aktif']],
    fields: [field('student.userId', 'Öğrenci', 'students'), field('course.courseId', 'Ders', 'courses'), field('active', 'Aktif', 'boolean')]
  },
  attendances: {
    title: 'Açık yoklamalar', url: '/attendances', requires: ['courses'],
    columns: [['course.courseCode', 'Ders'], ['attendanceType', 'Tür'], ['startedAt', 'Başlangıç'], ['expiresAt', 'Bitiş'], ['active', 'Açık']],
    fields: [field('course.courseId', 'Ders', 'courses'), field('attendanceType', 'Tür', 'enum', {values: ['QR_CODE', 'NFC', 'SIX_DIGIT_CODE']}), field('sessionHours', 'Süre (saat)', 'number', {value: '1'}), field('startedAt', 'Başlangıç', 'datetime-local'), field('expiresAt', 'Bitiş', 'datetime-local'), field('active', 'Aktif', 'boolean')]
  },
  records: {
    title: 'Yoklama kayıtları', url: '/attendance-records', requires: ['attendances', 'students'],
    columns: [['student.firstName', 'Öğrenci'], ['student.lastName', 'Soyad'], ['attendanceSession.attendance.course.courseCode', 'Ders'], ['attendanceType', 'Tür'], ['attendAt', 'Zaman'], ['late', 'Geç']]
  }
};
const groups = [
  {title: 'Akademik yapı', keys: ['languages', 'faculties', 'departments']},
  {title: 'Kişiler', keys: ['lecturers', 'students']},
  {title: 'Ders yönetimi', keys: ['courses', 'lecturerCourses', 'studentCourses']},
  {title: 'Yoklama', keys: ['attendances', 'records']}
];
const names = {
  languages: row => row.languageName,
  faculties: row => row.facultyName,
  departments: row => row.departmentCode + ' · ' + row.departmentName,
  lecturers: row => row.firstName + ' ' + row.lastName,
  students: row => row.firstName + ' ' + row.lastName + ' (' + (row.studentNumber || '') + ')',
  courses: row => row.courseCode + ' · ' + row.courseName
};
const ids = {languages: 'id', faculties: 'facultyId', departments: 'id', lecturers: 'userId', students: 'userId', courses: 'courseId'};

function message(value, ok = false) {
  $('message').textContent = value;
  $('message').className = ok ? 'ok' : '';
}

function showLogin(expired = false) {
  state.renderToken++;
  $('sidebar').hidden = true;
  $('workspace').hidden = true;
  $('login-panel').hidden = false;
  state.lists = {};
  state.availability = null;
  if (expired) message('Oturum süresi doldu. Lütfen yeniden giriş yapın.');
}

function showWorkspace() {
  $('login-panel').hidden = true;
  $('workspace').hidden = false;
  $('sidebar').hidden = false;
}

async function api(url, options = {}) {
  const response = await fetch(url, {
    credentials: 'same-origin',
    ...options,
    headers: {'X-Admin-Request': '1', ...(options.body ? {'Content-Type': 'application/json'} : {}), ...options.headers}
  });
  let payload;
  try { payload = await response.json(); } catch { payload = {}; }
  if (response.status === 401 && url !== '/admin/login') {
    showLogin(true);
    throw new Error('Oturum süresi doldu. Lütfen yeniden giriş yapın.');
  }
  if (!response.ok || payload.result === false) {
    throw new Error(payload.errorMessage || 'İstek başarısız (' + response.status + ')');
  }
  return payload.data ?? payload;
}

async function refreshAvailability() {
  state.availability = await api('/admin/availability');
}

function missingRequirements(key) {
  return (sections[key].requires || []).filter(required => !state.availability?.[required]);
}

function renderNav() {
  if (missingRequirements(state.section).length) state.section = 'languages';
  const nav = $('tabs');
  nav.replaceChildren();
  for (const group of groups) {
    const wrapper = document.createElement('div');
    wrapper.className = 'nav-group';
    const heading = document.createElement('p');
    heading.className = 'nav-label';
    heading.textContent = group.title;
    wrapper.append(heading);
    for (const key of group.keys) {
      const button = document.createElement('button');
      button.type = 'button';
      button.textContent = sections[key].title;
      button.className = key === state.section ? 'active' : '';
      button.setAttribute('aria-current', key === state.section ? 'page' : 'false');
      const missing = missingRequirements(key);
      button.disabled = missing.length > 0;
      if (missing.length) {
        const reason = document.createElement('span');
        reason.className = 'nav-reason';
        reason.textContent = 'Önce ' + missing.map(item => sections[item].title.toLocaleLowerCase('tr')).join(' ve ') + ' ekleyin';
        button.append(reason);
      }
      button.addEventListener('click', () => {
        state.section = key;
        renderNav();
        render();
      });
      wrapper.append(button);
    }
    nav.append(wrapper);
  }
}

function path(row, key) {
  return key.split('.').reduce((value, part) => value?.[part], row);
}

function put(target, key, value) {
  const parts = key.split('.');
  let cursor = target;
  for (const part of parts.slice(0, -1)) cursor = cursor[part] ??= {};
  cursor[parts.at(-1)] = value;
}

async function load(key, force = false) {
  if (!force && Array.isArray(state.lists[key])) return state.lists[key];
  const rows = await api(sections[key].url);
  if (!Array.isArray(rows)) throw new Error(sections[key].title + ' listesi okunamadı.');
  state.lists[key] = rows;
  return rows;
}

function makeField(spec) {
  const label = document.createElement('label');
  label.textContent = spec.label;
  let input;
  if (spec.type === 'boolean' || spec.type === 'enum' || sections[spec.type]) {
    input = document.createElement('select');
    let choices = [];
    if (spec.type === 'boolean') choices = [['true', 'Evet'], ['false', 'Hayır']];
    else if (spec.type === 'enum') choices = spec.values.map(value => [value, value]);
    else choices = (state.lists[spec.type] || []).map(row => [row[ids[spec.type]], names[spec.type](row)]);
    input.add(new Option('Seçin', ''));
    for (const [id, name] of choices) input.add(new Option(name, id));
  } else {
    input = document.createElement('input');
    input.type = spec.type;
    if (spec.step) input.step = spec.step;
    if (spec.value) input.value = spec.value;
  }
  input.name = spec.key;
  input.required = true;
  label.append(input);
  return label;
}

function renderForm(root, key, config) {
  if (!config.fields && !config.register) return;
  const heading = document.createElement('h2');
  heading.textContent = 'Yeni kayıt';
  root.append(heading);
  if (config.register) {
    const note = document.createElement('p');
    note.className = 'form-note';
    note.textContent = 'Bu form kullanıcı hesabını ve ' + (config.register === 'STUDENT' ? 'öğrenci' : 'öğretmen') + ' profilini birlikte oluşturur.';
    root.append(note);
  }
  const form = document.createElement('form');
  form.className = 'form-grid';
  const fields = config.register
    ? [field('firstName', 'Ad'), field('lastName', 'Soyad'), field('email', 'E-posta', 'email'), field('password', 'Geçici şifre', 'password'), field('gender', 'Cinsiyet', 'enum', {values: ['FEMALE', 'MALE', 'OTHER']}), field('departmentId', 'Bölüm', 'departments')]
    : config.fields;
  for (const spec of fields) form.append(makeField(spec));
  const button = document.createElement('button');
  button.type = 'submit';
  button.textContent = 'Kaydet';
  form.append(button);
  form.addEventListener('submit', async event => {
    event.preventDefault();
    button.disabled = true;
    message('');
    const body = {};
    for (const spec of fields) {
      const value = form.elements[spec.key].value;
      put(body, spec.key, spec.type === 'number' ? Number(value) : spec.type === 'boolean' ? value === 'true' : value);
    }
    if (config.register) {
      body.userType = config.register;
      body.departmentId = Number(body.departmentId);
    }
    try {
      await api(config.register ? '/admin/register' : config.url, {method: 'POST', body: JSON.stringify(body)});
      state.pages[key] = 0;
      await load(key, true);
      await refreshAvailability();
      renderNav();
      if (await render()) message('Kayıt eklendi; tablo yenilendi.', true);
    } catch (error) {
      message(error.message);
    } finally {
      button.disabled = false;
    }
  });
  root.append(form);
  const divider = document.createElement('div');
  divider.className = 'section-divider';
  root.append(divider);
}

function renderTable(root, key, config) {
  const rows = key === 'attendances' ? state.lists[key].filter(row => row.active) : state.lists[key];
  const heading = document.createElement('div');
  heading.className = 'table-heading';
  const title = document.createElement('h2');
  title.textContent = key === 'attendances' ? 'Açık kayıtlar' : 'Kayıtlar';
  const count = document.createElement('p');
  count.className = 'muted';
  count.textContent = rows.length + ' kayıt';
  heading.append(title, count);
  root.append(heading);
  if (!rows.length) {
    const empty = document.createElement('p');
    empty.className = 'empty';
    empty.textContent = 'Henüz kayıt yok.';
    root.append(empty);
    return;
  }
  const pageSize = 40;
  const pages = Math.ceil(rows.length / pageSize);
  const page = Math.min(state.pages[key] || 0, pages - 1);
  state.pages[key] = page;
  const wrap = document.createElement('div');
  wrap.className = 'table-wrap';
  const table = document.createElement('table');
  const head = document.createElement('thead');
  const header = document.createElement('tr');
  for (const [, label] of config.columns) {
    const th = document.createElement('th');
    th.textContent = label;
    header.append(th);
  }
  head.append(header);
  table.append(head);
  const body = document.createElement('tbody');
  for (const row of rows.slice(page * pageSize, (page + 1) * pageSize)) {
    const tr = document.createElement('tr');
    for (const [property] of config.columns) {
      const td = document.createElement('td');
      const value = path(row, property);
      td.textContent = value == null ? '—' : typeof value === 'boolean' ? (value ? 'Evet' : 'Hayır') : String(value);
      tr.append(td);
    }
    body.append(tr);
  }
  table.append(body);
  wrap.append(table);
  root.append(wrap);
  if (pages > 1) {
    const pager = document.createElement('div');
    pager.className = 'pager';
    const previous = document.createElement('button');
    previous.type = 'button';
    previous.textContent = 'Önceki';
    previous.disabled = page === 0;
    previous.onclick = () => { state.pages[key] = page - 1; render(); };
    const position = document.createElement('span');
    position.textContent = (page + 1) + ' / ' + pages;
    const next = document.createElement('button');
    next.type = 'button';
    next.textContent = 'Sonraki';
    next.disabled = page + 1 >= pages;
    next.onclick = () => { state.pages[key] = page + 1; render(); };
    pager.append(previous, position, next);
    root.append(pager);
  }
}

async function render() {
  const token = ++state.renderToken;
  const key = state.section;
  const config = sections[key];
  message('');
  $('page-title').textContent = config.title;
  try {
    const dependencies = [...new Set([...(config.fields || []).map(spec => spec.type).filter(type => sections[type]), ...(config.register ? ['departments'] : []), key])];
    await Promise.all(dependencies.map(loadKey => load(loadKey)));
    if (token !== state.renderToken) return false;
    const root = $('content');
    root.replaceChildren();
    renderForm(root, key, config);
    renderTable(root, key, config);
    return true;
  } catch (error) {
    if (token === state.renderToken) message(error.message);
    return false;
  }
}

async function openWorkspace() {
  showWorkspace();
  await refreshAvailability();
  renderNav();
  await render();
}

$('login-form').addEventListener('submit', async event => {
  event.preventDefault();
  const form = event.currentTarget;
  const button = form.querySelector('button');
  button.disabled = true;
  try {
    await api('/admin/login', {method: 'POST', body: JSON.stringify({email: form.email.value, password: form.password.value})});
    form.reset();
    await openWorkspace();
  } catch (error) {
    message(error.message);
  } finally {
    button.disabled = false;
  }
});

$('refresh').addEventListener('click', async event => {
  const button = event.currentTarget;
  button.disabled = true;
  try {
    state.lists = {};
    await refreshAvailability();
    renderNav();
    await render();
  } catch (error) {
    message(error.message);
  } finally {
    button.disabled = false;
  }
});

$('logout').addEventListener('click', async () => {
  try { await api('/admin/logout', {method: 'POST'}); }
  finally {
    showLogin();
    message('Çıkış yapıldı.', true);
  }
});

api('/admin/session').then(openWorkspace).catch(() => {
  showLogin();
  message('');
});
