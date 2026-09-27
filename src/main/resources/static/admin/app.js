'use strict';
const $ = id => document.getElementById(id);
const state = {section:'languages', lists:{}};
const field = (key,label,type='text',extra={}) => ({key,label,type,...extra});
const sections = {
  languages:{title:'Diller',url:'/languages',columns:[['id','ID'],['languageName','Dil']],fields:[field('languageName','Dil adı')]},
  faculties:{title:'Fakülteler',url:'/faculties',columns:[['facultyId','ID'],['facultyName','Fakülte']],fields:[field('facultyName','Fakülte adı')]},
  departments:{title:'Bölümler',url:'/departments',columns:[['id','ID'],['departmentName','Bölüm'],['departmentCode','Kod'],['faculty.facultyName','Fakülte'],['language.languageName','Dil']],fields:[field('departmentName','Bölüm adı'),field('departmentCode','Kod'),field('faculty.facultyId','Fakülte','faculties'),field('language.id','Dil','languages')]},
  lecturers:{title:'Öğretmenler',url:'/lecturers',columns:[['firstName','Ad'],['lastName','Soyad'],['department.departmentName','Bölüm'],['userId','ID']],register:'LECTURER'},
  students:{title:'Öğrenciler',url:'/students',columns:[['firstName','Ad'],['lastName','Soyad'],['studentNumber','Numara'],['department.departmentName','Bölüm'],['userId','ID']],register:'STUDENT'},
  courses:{title:'Dersler',url:'/courses',columns:[['courseCode','Kod'],['courseName','Ders'],['department.departmentName','Bölüm'],['courseId','ID']],fields:[field('courseName','Ders adı'),field('courseCode','Kod'),field('courseEcts','AKTS','number',{step:'0.1'}),field('courseCredit','Kredi','number',{step:'0.1'}),field('hoursTheoretical','Teorik saat','number'),field('hoursPractical','Uygulama saati','number'),field('minAttendancePercent','Asgari devam %','number'),field('language.id','Dil','languages'),field('department.id','Bölüm','departments'),field('online','Çevrimiçi','boolean'),field('elective','Seçmeli','boolean')]},
  lecturerCourses:{title:'Öğretmen–ders',url:'/lecturer-courses',columns:[['lecturer.firstName','Öğretmen'],['lecturer.lastName','Soyad'],['course.courseCode','Ders'],['active','Aktif']],fields:[field('lecturer.userId','Öğretmen','lecturers'),field('course.courseId','Ders','courses'),field('active','Aktif','boolean')]},
  studentCourses:{title:'Öğrenci–ders',url:'/student-courses',columns:[['student.firstName','Öğrenci'],['student.lastName','Soyad'],['course.courseCode','Ders'],['active','Aktif']],fields:[field('student.userId','Öğrenci','students'),field('course.courseId','Ders','courses'),field('active','Aktif','boolean')]},
  attendances:{title:'Yoklamalar',url:'/attendances',columns:[['course.courseCode','Ders'],['attendanceType','Tür'],['startedAt','Başlangıç'],['expiresAt','Bitiş'],['active','Açık']],fields:[field('course.courseId','Ders','courses'),field('attendanceType','Tür','enum',{values:['QR_CODE','NFC','SIX_DIGIT_CODE']}),field('sessionHours','Süre (saat)','number',{value:'1'}),field('startedAt','Başlangıç','datetime-local'),field('expiresAt','Bitiş','datetime-local'),field('active','Aktif','boolean')]},
  records:{title:'Yoklama kayıtları',url:'/attendance-records',columns:[['student.firstName','Öğrenci'],['student.lastName','Soyad'],['attendance.course.courseCode','Ders'],['attendanceType','Tür'],['attendAt','Zaman'],['late','Geç']]}
};
const names = {languages:r=>r.languageName,faculties:r=>r.facultyName,departments:r=>`${r.departmentCode} · ${r.departmentName}`,lecturers:r=>`${r.firstName} ${r.lastName}`,students:r=>`${r.firstName} ${r.lastName} (${r.studentNumber||''})`,courses:r=>`${r.courseCode} · ${r.courseName}`};
const ids = {languages:'id',faculties:'facultyId',departments:'id',lecturers:'userId',students:'userId',courses:'courseId'};
function message(value,ok=false){$('message').textContent=value;$('message').className=ok?'ok':'';}
function showLogin(expired=false){$('workspace').hidden=true;$('login-panel').hidden=false;$('logout').hidden=true;if(expired)message('Oturum süresi doldu. Lütfen yeniden giriş yapın.');}
async function api(url,options={}){
  const response=await fetch(url,{credentials:'same-origin',...options,headers:{'X-Admin-Request':'1',...(options.body?{'Content-Type':'application/json'}:{}),...options.headers}});
  let payload;try{payload=await response.json();}catch{payload={};}
  if(response.status===401 && url!='/admin/login'){
    showLogin(true);
    throw new Error('Oturum süresi doldu. Lütfen yeniden giriş yapın.');
  }
  if(!response.ok || payload.result===false)throw new Error(payload.errorMessage || `İstek başarısız (${response.status})`);
  return payload.data ?? payload;
}
function path(row,key){return key.split('.').reduce((value,part)=>value?.[part],row);}
function put(target,key,value){const parts=key.split('.');let cursor=target;for(const part of parts.slice(0,-1))cursor=cursor[part]??={};cursor[parts.at(-1)]=value;}
async function load(key){state.lists[key]=await api(sections[key].url);return state.lists[key];}
function makeField(spec){
  const label=document.createElement('label');label.textContent=spec.label;
  let input;if(spec.type==='boolean'||spec.type==='enum'||sections[spec.type]){
    input=document.createElement('select');let choices=[];
    if(spec.type==='boolean')choices=[['true','Evet'],['false','Hayır']];
    else if(spec.type==='enum')choices=spec.values.map(x=>[x,x]);
    else choices=(state.lists[spec.type]||[]).map(row=>[row[ids[spec.type]],names[spec.type](row)]);
    const empty=new Option('Seçin','');input.add(empty);
    for(const [id,name] of choices)input.add(new Option(name,id));
  }else{input=document.createElement('input');input.type=spec.type;input.step=spec.step||'any';if(spec.value)input.value=spec.value;}
  input.name=spec.key;input.required=true;label.append(input);return label;
}
async function render(){
  const key=state.section, config=sections[key];message('');
  try{
    const deps=[...new Set([...(config.fields||[]).map(f=>f.type).filter(t=>sections[t]),...(config.register?['departments']:[])])];
    await Promise.all([...deps,key].map(load));
    const root=$('content');root.replaceChildren();const h=document.createElement('h2');h.textContent=config.title;root.append(h);
    if(config.fields||config.register){
      const form=document.createElement('form');form.className='form-grid';
      const fields=config.register?[field('firstName','Ad'),field('lastName','Soyad'),field('email','E-posta','email'),field('password','Geçici şifre','password'),field('gender','Cinsiyet','enum',{values:['FEMALE','MALE','OTHER']}),field('departmentId','Bölüm','departments')]:config.fields;
      for(const spec of fields)form.append(makeField(spec));
      const button=document.createElement('button');button.textContent='Kaydet';form.append(button);
      form.addEventListener('submit',async e=>{e.preventDefault();button.disabled=true;message('');const body={};for(const spec of fields){const value=form.elements[spec.key].value;put(body,spec.key,spec.type==='number'?Number(value):spec.type==='boolean'?value==='true':value);}
        if(config.register){body.userType=config.register;body.departmentId=Number(body.departmentId);}
        try{await api(config.register?'/admin/register':config.url,{method:'POST',body:JSON.stringify(body)});await render();message('Kayıt eklendi; tablo yenilendi.',true);}catch(error){message(error.message);}finally{button.disabled=false;}
      });root.append(form);
    }
    const caption=document.createElement('p');caption.className='muted';caption.textContent=`${state.lists[key].length} kayıt`;root.append(caption);
    const wrap=document.createElement('div');wrap.className='table-wrap';const table=document.createElement('table');const head=document.createElement('thead');const header=document.createElement('tr');for(const [,title] of config.columns){const th=document.createElement('th');th.textContent=title;header.append(th);}head.append(header);table.append(head);
    const body=document.createElement('tbody');for(const row of state.lists[key]){if(key==='attendances' && !row.active)continue;const tr=document.createElement('tr');for(const [prop] of config.columns){const td=document.createElement('td');td.textContent=String(path(row,prop)??'—');tr.append(td);}body.append(tr);}table.append(body);wrap.append(table);root.append(wrap);
  }catch(error){message(error.message);}
}
for(const [key,config] of Object.entries(sections)){const button=document.createElement('button');button.textContent=config.title;button.onclick=()=>{state.section=key;for(const tab of $('tabs').children)tab.classList.remove('active');button.classList.add('active');render();};if(key===state.section)button.classList.add('active');$('tabs').append(button);}
$('login-form').addEventListener('submit',async event=>{event.preventDefault();const form=event.currentTarget;const button=form.querySelector('button');button.disabled=true;try{await api('/admin/login',{method:'POST',body:JSON.stringify({email:form.email.value,password:form.password.value})});form.reset();$('login-panel').hidden=true;$('workspace').hidden=false;$('logout').hidden=false;await render();}catch(error){message(error.message);}finally{button.disabled=false;}});
$('logout').onclick=async()=>{try{await api('/admin/logout',{method:'POST'});}finally{showLogin();message('Çıkış yapıldı.',true);}};
api('/admin/session').then(()=>{$('login-panel').hidden=true;$('workspace').hidden=false;$('logout').hidden=false;render();}).catch(()=>{showLogin();message('');});
