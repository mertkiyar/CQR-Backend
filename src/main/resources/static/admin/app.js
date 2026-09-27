'use strict';

const $ = id => document.getElementById(id);
const state = {section: 'languages', lists: {}, availability: null, pages: {}, renderToken: 0};
const field = (key, label, type = 'text', extra = {}) => ({key, label, type, ...extra});
const sections = {
  languages: {
    title: 'Languages', url: '/languages',
    columns: [['id', 'ID'], ['languageName', 'Language']],
    fields: [field('languageName', 'Language name')]
  },
  faculties: {
    title: 'Faculties', url: '/faculties',
    columns: [['facultyId', 'ID'], ['facultyName', 'Faculty']],
    fields: [field('facultyName', 'Faculty name')]
  },
  departments: {
    title: 'Departments', url: '/departments', requires: ['languages', 'faculties'],
    columns: [['id', 'ID'], ['departmentName', 'Department'], ['departmentCode', 'Code'], ['faculty.facultyName', 'Faculty'], ['language.languageName', 'Language']],
    fields: [field('departmentName', 'Department name'), field('departmentCode', 'Code'), field('faculty.facultyId', 'Faculty', 'faculties'), field('language.id', 'Language', 'languages')]
  },
  lecturers: {
    title: 'Lecturers', url: '/lecturers', requires: ['departments'], register: 'LECTURER',
    columns: [['firstName', 'First name'], ['lastName', 'Last name'], ['department.departmentName', 'Department'], ['userId', 'ID']]
  },
  students: {
    title: 'Students', url: '/students', requires: ['departments'], register: 'STUDENT',
    columns: [['firstName', 'First name'], ['lastName', 'Last name'], ['studentNumber', 'Student number'], ['department.departmentName', 'Department'], ['userId', 'ID']]
  },
  courses: {
    title: 'Courses', url: '/courses', requires: ['languages', 'departments'],
    columns: [['courseCode', 'Code'], ['courseName', 'Course'], ['department.departmentName', 'Department'], ['courseId', 'ID']],
    fields: [field('courseName', 'Course name'), field('courseCode', 'Code'), field('courseEcts', 'ECTS', 'number', {step: '0.1'}), field('courseCredit', 'Credits', 'number', {step: '0.1'}), field('hoursTheoretical', 'Theory hours', 'number'), field('hoursPractical', 'Practical hours', 'number'), field('minAttendancePercent', 'Minimum attendance (%)', 'number'), field('language.id', 'Language', 'languages'), field('department.id', 'Department', 'departments'), field('online', 'Online', 'boolean'), field('elective', 'Elective', 'boolean')]
  },
  lecturerCourses: {
    title: 'Lecturer courses', url: '/lecturer-courses', requires: ['lecturers', 'courses'],
    columns: [['lecturer.firstName', 'Lecturer'], ['lecturer.lastName', 'Last name'], ['course.courseCode', 'Course'], ['active', 'Active']],
    fields: [field('lecturer.userId', 'Lecturer', 'lecturers'), field('course.courseId', 'Course', 'courses'), field('active', 'Active', 'boolean')]
  },
  studentCourses: {
    title: 'Student courses', url: '/student-courses', requires: ['students', 'courses'],
    columns: [['student.firstName', 'Student'], ['student.lastName', 'Last name'], ['course.courseCode', 'Course'], ['active', 'Active']],
    fields: [field('student.userId', 'Student', 'students'), field('course.courseId', 'Course', 'courses'), field('active', 'Active', 'boolean')]
  },
  attendances: {
    title: 'Attendance', url: '/attendances', requires: ['courses'],
    columns: [['course.courseCode', 'Course'], ['attendanceType', 'Type'], ['startedAt', 'Starts at'], ['expiresAt', 'Expires at'], ['active', 'Open']],
    fields: [field('course.courseId', 'Course', 'courses'), field('attendanceType', 'Type', 'enum', {values: ['QR_CODE', 'NFC', 'SIX_DIGIT_CODE']}), field('sessionHours', 'Duration (hours)', 'number', {value: '1'}), field('startedAt', 'Starts at', 'datetime-local'), field('expiresAt', 'Expires at', 'datetime-local'), field('active', 'Active', 'boolean')]
  },
  records: {
    title: 'Attendance records', url: '/attendance-records', requires: ['attendances', 'students'],
    columns: [['student.firstName', 'Student'], ['student.lastName', 'Last name'], ['attendanceSession.attendance.course.courseCode', 'Course'], ['attendanceType', 'Type'], ['attendAt', 'Recorded at'], ['late', 'Late']]
  }
};
const groups = [
  {title: 'Academic structure', keys: ['languages', 'faculties', 'departments']},
  {title: 'People', keys: ['lecturers', 'students']},
  {title: 'Course management', keys: ['courses', 'lecturerCourses', 'studentCourses']},
  {title: 'Attendance', keys: ['attendances', 'records']}
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
const enumLabels = {FEMALE: 'Female', MALE: 'Male', OTHER: 'Other', QR_CODE: 'QR code', NFC: 'NFC', SIX_DIGIT_CODE: 'Six-digit code'};

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
  if (expired) message('Session expired. Please sign in again.');
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
    throw new Error('Session expired. Please sign in again.');
  }
  if (!response.ok || payload.result === false) {
    throw new Error(payload.errorMessage || 'Request failed (' + response.status + ')');
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
        reason.textContent = 'Add ' + missing.map(item => sections[item].title.toLocaleLowerCase('en')).join(' and ') + ' first';
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
  if (!Array.isArray(rows)) throw new Error(sections[key].title + ' could not be loaded.');
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
    if (spec.type === 'boolean') choices = [['true', 'Yes'], ['false', 'No']];
    else if (spec.type === 'enum') choices = spec.values.map(value => [value, enumLabels[value] || value]);
    else choices = (state.lists[spec.type] || []).map(row => [row[ids[spec.type]], names[spec.type](row)]);
    input.add(new Option('Select', ''));
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
  heading.textContent = 'New entry';
  root.append(heading);
  if (config.register) {
    const note = document.createElement('p');
    note.className = 'form-note';
    note.textContent = 'This form creates a user account and a ' + (config.register === 'STUDENT' ? 'student' : 'lecturer') + ' profile together.';
    root.append(note);
  }
  const form = document.createElement('form');
  form.className = 'form-grid';
  const fields = config.register
    ? [field('firstName', 'First name'), field('lastName', 'Last name'), field('email', 'Email', 'email'), field('password', 'Temporary password', 'password'), field('gender', 'Gender', 'enum', {values: ['FEMALE', 'MALE', 'OTHER']}), field('departmentId', 'Department', 'departments')]
    : config.fields;
  for (const spec of fields) form.append(makeField(spec));
  const button = document.createElement('button');
  button.type = 'submit';
  button.textContent = 'Save';
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
      if (await render()) message('Saved. The table has been refreshed.', true);
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
  title.textContent = key === 'attendances' ? 'Open sessions' : 'Records';
  const count = document.createElement('p');
  count.className = 'muted';
  count.textContent = rows.length + (rows.length === 1 ? ' record' : ' records');
  heading.append(title, count);
  root.append(heading);
  if (!rows.length) {
    const empty = document.createElement('p');
    empty.className = 'empty';
    empty.textContent = 'No records yet.';
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
      td.textContent = value == null ? '—' : typeof value === 'boolean' ? (value ? 'Yes' : 'No') : (enumLabels[value] || String(value));
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
    previous.textContent = 'Previous';
    previous.disabled = page === 0;
    previous.onclick = () => { state.pages[key] = page - 1; render(); };
    const position = document.createElement('span');
    position.textContent = (page + 1) + ' / ' + pages;
    const next = document.createElement('button');
    next.type = 'button';
    next.textContent = 'Next';
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
    message('Signed out.', true);
  }
});

api('/admin/session').then(openWorkspace).catch(() => {
  showLogin();
  message('');
});
