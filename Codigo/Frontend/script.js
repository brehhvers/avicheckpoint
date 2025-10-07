const UF_LIST = [
  "AC",
  "AL",
  "AM",
  "AP",
  "BA",
  "CE",
  "DF",
  "ES",
  "GO",
  "MA",
  "MG",
  "MS",
  "MT",
  "PA",
  "PB",
  "PE",
  "PI",
  "PR",
  "RJ",
  "RN",
  "RO",
  "RR",
  "RS",
  "SC",
  "SE",
  "SP",
  "TO",
];

const CONFIG = {
  cadastro: {
    titulo: "Cadastro",
    secoes: [
      {
        id: "cad-produtor",
        titulo: "Cadastro do produtor",
        campos: [
          { id: "p_nome", rotulo: "Nome", tipo: "text", required: true },
          { id: "p_email", rotulo: "Email", tipo: "text", required: true },
          {
            id: "p_senha",
            rotulo: "Senha",
            tipo: "text",
            placeholder: "Mín. 8, com maiúscula, minúscula e símbolo",
            required: true,
          },
          {
            id: "p_uf",
            rotulo: "Estado (UF)",
            tipo: "select",
            opcoes: UF_LIST,
            required: true,
          },
          {
            id: "p_cidade",
            rotulo: "Cidade",
            tipo: "text",
            placeholder: "Digite sua cidade",
            required: true,
          },
        ],
      },
      {
        id: "cad-profissional",
        titulo: "Cadastro de profissionais",
        campos: [
          { id: "pr_nome", rotulo: "Nome", tipo: "text", required: true },
          { id: "pr_email", rotulo: "Email", tipo: "text", required: true },
          {
            id: "pr_senha",
            rotulo: "Senha",
            tipo: "text",
            placeholder: "Mín. 8, com maiúscula, minúscula e símbolo",
            required: true,
          },
          {
            id: "pr_uf",
            rotulo: "Estado (UF)",
            tipo: "select",
            opcoes: UF_LIST,
            required: true,
          },
          { id: "pr_cidade", rotulo: "Cidade", tipo: "text", required: true },
          {
            id: "pr_formacao",
            rotulo: "Formação",
            tipo: "select",
            opcoes: ["Medicina veterinária", "Zootecnia", "Medicina"],
            required: true,
          },
          {
            id: "pr_bio",
            rotulo: "Sobre o profissional",
            tipo: "textarea",
            placeholder: "Escreva um breve resumo",
          },
        ],
      },
    ],
  },

  sobre: {
    titulo: "Quem somos nós?",
    secoes: [{ id: "team", titulo: "Equipe", campos: [] }],
  },

  saude: {
    titulo: "Saúde das aves",
    secoes: [
      {
        id: "sinais",
        titulo: "Sinais e vacinação",
        campos: [
          {
            id: "s1",
            rotulo: "Você observou aves com sinais de doença?",
            tipo: "yesno",
            required: true,
          },
          {
            id: "s2",
            rotulo: "Quais sinais clínicos foram vistos?",
            tipo: "select",
            opcoes: [
              "Sem alterações",
              "Espirros",
              "Diarreia",
              "Apatia",
              "Queda na produção",
            ],
          },
          {
            id: "s3",
            rotulo: "A vacinação das aves está em dia?",
            tipo: "yesno",
            required: true,
          },
          {
            id: "s4",
            rotulo: "Ao introduzir novas aves, é feita quarentena?",
            tipo: "yesno",
            required: true,
          },
          {
            id: "s5",
            rotulo: "Existe acesso de aves silvestres ao galpão?",
            tipo: "yesno",
            required: true,
          },
        ],
      },
    ],
  },

  nutricao: {
    titulo: "Nutrição",
    secoes: [
      {
        id: "alimento",
        titulo: "Alimentação e armazenamento",
        campos: [
          {
            id: "n1",
            rotulo: "Tipo de alimentação",
            tipo: "select",
            opcoes: [
              "Ração industrial",
              "Comida caseira",
              "Sobras de alimentos",
            ],
            required: true,
          },
          {
            id: "n2",
            rotulo:
              "O alimento é armazenado em local fechado, seco e protegido de pragas?",
            tipo: "yesno",
            required: true,
          },
          {
            id: "n3",
            rotulo: "Há presença de fungos, bolor ou insetos na ração?",
            tipo: "yesno",
            required: true,
          },
        ],
      },
    ],
  },

  avaliacao: {
    titulo: "Avaliação do ovo",
    secoes: [
      {
        id: "registro_luz",
        titulo: "Registros e fotoperíodo",
        campos: [
          {
            id: "a1",
            rotulo:
              "Você realiza anotações ou registros do desempenho zootécnico das suas aves (produção de ovos, consumo de ração, mortalidade, etc.)?",
            tipo: "yesno",
            required: true,
          },
          {
            id: "a2",
            rotulo: "A iluminação segue manejo adequado (fotoperíodo)?",
            tipo: "yesno",
            required: true,
          },
        ],
      },
      {
        id: "qualidade_ovo",
        titulo: "Qualidade e coleta",
        campos: [
          {
            id: "a3",
            rotulo:
              "Como você avalia a casca dos ovos produzidos pelas suas aves?",
            tipo: "select",
            opcoes: [
              "Sem alteração e uniforme",
              "Rachaduras/fissuras/manchas",
              "Rugosa/com relevo",
            ],
            required: true,
          },
          {
            id: "a4",
            rotulo: "Como os ovos estão no momento da coleta?",
            tipo: "select",
            opcoes: ["Sem alteração", "Presença de sujidades/fezes"],
            required: true,
          },
          { id: "a5", rotulo: "Coloração da gema (escala 1–16)", tipo: "yolk" },
        ],
      },
    ],
  },

  doencas: {
    titulo: "Doenças (Influenza aviária / Newcastle)",
    secoes: [
      {
        id: "triagem",
        titulo: "Triagem rápida",
        campos: [
          {
            id: "d1",
            rotulo: "Mortes repentinas nos últimos dias?",
            tipo: "yesno",
            required: true,
          },
          {
            id: "d2",
            rotulo: "Queda repentina na produção de ovos?",
            tipo: "yesno",
            required: true,
          },
          {
            id: "d3",
            rotulo: "Apatia/fraqueza/dificuldade de locomoção?",
            tipo: "yesno",
            required: true,
          },
          {
            id: "d4",
            rotulo: "Tremores/torcicolo/andar cambaleante?",
            tipo: "yesno",
            required: true,
          },
          {
            id: "d5",
            rotulo: "Tosse/espirros/chiado?",
            tipo: "yesno",
            required: true,
          },
          {
            id: "d6",
            rotulo: "Diarreia verde ou aquosa?",
            tipo: "yesno",
            required: true,
          },
          {
            id: "d7",
            rotulo: "Contato com aves silvestres?",
            tipo: "yesno",
            required: true,
          },
        ],
      },
    ],
  },

  resultado: { titulo: "Resultado", secoes: [] },
  sabia: {
    titulo: "Você sabia?",
    secoes: [{ id: "edu", titulo: "Materiais educativos", campos: [] }],
  },
};

const valuesState = {};
let currentTab = "cadastro";
const order = [
  "cadastro",
  "sobre",
  "saude",
  "nutricao",
  "avaliacao",
  "doencas",
  "resultado",
  "sabia",
];

const page = document.getElementById("page");
const prevBtn = document.getElementById("prevBtn");
const nextBtn = document.getElementById("nextBtn");

document
  .querySelectorAll("#tabs .pill")
  .forEach((b) => b.addEventListener("click", () => goTo(b.dataset.tab)));
const tabButtons = () => Array.from(document.querySelectorAll("#tabs .pill"));
function setActivePill(tabKey) {
  tabButtons().forEach((b) =>
    b.classList.toggle("active", b.dataset.tab === tabKey)
  );
}
function goTo(tab) {
  currentTab = tab;
  render();
  updateNavButtons();
  setActivePill(tab);
}
function updateNavButtons() {
  const i = order.indexOf(currentTab);
  prevBtn.disabled = i <= 0;
  nextBtn.disabled = i >= order.length - 1;
}
prevBtn.addEventListener("click", () =>
  goTo(order[Math.max(0, order.indexOf(currentTab) - 1)])
);
nextBtn.addEventListener("click", () =>
  goTo(order[Math.min(order.length - 1, order.indexOf(currentTab) + 1)])
);

function validatePassword(pw) {
  if (!pw) return false;
  return (
    pw.length >= 8 &&
    /[A-Z]/.test(pw) &&
    /[a-z]/.test(pw) &&
    /[^A-Za-z0-9]/.test(pw)
  );
}

function saveProducer() {
  const get = (id) => document.getElementById(id)?.value?.trim();
  const produtor = {
    nome: get("p_nome"),
    email: get("p_email"),
    senha: get("p_senha"),
    uf: get("p_uf"),
    cidade: get("p_cidade"),
  };
  if (!validatePassword(produtor.senha)) {
    alert("Senha inválida: mínimo 8, com maiúscula, minúscula e símbolo.");
    return;
  }
  if (!produtor.nome || !produtor.email || !produtor.uf || !produtor.cidade) {
    alert("Preencha os campos obrigatórios.");
    return;
  }
  localStorage.setItem("produtor", JSON.stringify(produtor));
  localStorage.setItem("ufProd", produtor.uf);
  alert(
    "Produtor salvo (demo). Substitua por chamada de API quando integrar o backend."
  );
}

function saveProfessional() {
  const get = (id) => document.getElementById(id)?.value?.trim();
  const prof = {
    nome: get("pr_nome"),
    email: get("pr_email"),
    senha: get("pr_senha"),
    uf: get("pr_uf"),
    cidade: get("pr_cidade"),
    formacao: get("pr_formacao"),
    bio: document.getElementById("pr_bio")?.value?.trim(),
  };
  if (!validatePassword(prof.senha))
    return alert(
      "Senha inválida: mínimo 8, com maiúscula, minúscula e símbolo."
    );
  if (!prof.nome || !prof.email || !prof.uf || !prof.cidade || !prof.formacao)
    return alert("Preencha os campos obrigatórios.");
  // demo: empilha no localStorage (substituir por API POST /profissionais)
  const list = JSON.parse(localStorage.getItem("profissionais") || "[]");
  list.push({ ...prof, id: Date.now() });
  localStorage.setItem("profissionais", JSON.stringify(list));
  alert(
    "Profissional salvo (demo). Substitua por chamada de API quando integrar o backend."
  );
}

function filterProfessionalsSuggestion(formacao) {
  const list = JSON.parse(localStorage.getItem("profissionais") || "[]");
  const ufProd =
    valuesState.cadastro?.p_uf || localStorage.getItem("ufProd") || null;
  let filtered = list;
  if (ufProd) filtered = filtered.filter((p) => p.uf === ufProd);
  if (formacao)
    filtered = filtered.filter((p) =>
      (p.formacao || "").toLowerCase().includes(formacao.toLowerCase())
    );
  return filtered.slice(0, 5);
}

function makeControl({
  id,
  rotulo,
  tipo,
  opcoes,
  placeholder,
  required,
  step,
}) {
  const wrap = document.createElement("div");
  wrap.className = "question";
  wrap.dataset.qid = id;

  const lab = document.createElement("label");
  lab.htmlFor = id;
  lab.textContent = rotulo;

  let el;

  switch (tipo) {
    case "number": {
      el = document.createElement("input");
      el.type = "number";
      el.id = id;
      el.placeholder = placeholder || "";
      el.inputMode = "numeric";
      if (step) el.step = step;
      break;
    }
    case "text": {
      el = document.createElement("input");
      el.type = "text";
      el.id = id;
      el.placeholder = placeholder || "";
      break;
    }
    case "textarea": {
      el = document.createElement("textarea");
      el.id = id;
      el.placeholder = placeholder || "";
      break;
    }
    case "yesno": {
      el = document.createElement("select");
      el.id = id;
      el.append(new Option("Selecione", "", true, true));
      ["Sim", "Não"].forEach((v) => el.append(new Option(v, v)));
      break;
    }
    case "select": {
      el = document.createElement("select");
      el.id = id;
      el.append(new Option("Selecione", "", true, true));
      (opcoes || []).forEach((o) => el.append(new Option(o, o)));
      break;
    }
    case "yolk": {
      const yolkHex = [
        "#f0d78b",
        "#f3d370",
        "#f4d264",
        "#fad54a",
        "#fcd107",
        "#fcd107",
        "#fdce00",
        "#fdce00",
        "#fdce00",
        "#fdc100",
        "#fcb600",
        "#f7a902",
        "#f6a417",
        "#f39725",
        "#ef8829",
        "#ed6c25",
      ];
      const palette = document.createElement("div");
      palette.className = "yolk-palette";
      palette.setAttribute("role", "radiogroup");
      palette.setAttribute("aria-label", "Coloração da gema (1 a 16)");

      const hidden = document.createElement("input");
      hidden.type = "hidden";
      hidden.id = id;

      let selectedIndex = null;
      const selectIndex = (idx) => {
        selectedIndex = idx;
        [...palette.children].forEach(
          (b, i) => (b.dataset.selected = i + 1 === idx ? "true" : "false")
        );
        hidden.value = String(idx);
        valuesState[currentTab] ??= {};
        valuesState[currentTab][id] = String(idx);
      };

      yolkHex.forEach((hex, i) => {
        const btn = document.createElement("button");
        btn.type = "button";
        btn.className = "yolk-swatch";
        btn.style.background = hex;
        btn.setAttribute("role", "radio");
        btn.setAttribute("aria-label", `Nível ${i + 1}`);
        btn.dataset.selected = "false";
        btn.addEventListener("click", () => selectIndex(i + 1));
        palette.appendChild(btn);
      });

      const legend = document.createElement("div");
      legend.className = "yolk-legend";
      legend.textContent =
        "Selecione o tom mais próximo (1 = mais claro • 16 = mais escuro).";

      const prev = valuesState[currentTab]?.[id];
      if (prev) selectIndex(+prev);

      const frag = document.createDocumentFragment();
      frag.append(palette, hidden, legend);
      el = frag;
      break;
    }
    default: {
      el = document.createElement("select");
      el.id = id;
      el.append(new Option("Selecione", "", true, true));
      (opcoes || []).forEach((o) => el.append(new Option(o, o)));
      break;
    }
  }

  if (required && el instanceof HTMLElement && "required" in el)
    el.required = true;

  if (["number", "text", "textarea", "yesno", "select"].includes(tipo)) {
    el.addEventListener?.("input", () => {
      valuesState[currentTab] ??= {};
      valuesState[currentTab][id] = el.value;
    });
  }

  wrap.append(lab, el);
  return wrap;
}

function render() {
  const conf = CONFIG[currentTab];
  page.innerHTML = "";

  if (currentTab === "sobre") return renderSobre();
  if (currentTab === "resultado") return renderResultado();
  if (currentTab === "sabia") return renderSabia();

  conf.secoes.forEach((sec) => {
    const card = document.createElement("section");
    card.className = "card";
    card.dataset.sectionId = sec.id;
    const h = document.createElement("h2");
    h.textContent = sec.titulo;
    card.appendChild(h);
    sec.campos.forEach((c) => card.appendChild(makeControl(c)));

    if (currentTab === "cadastro" && sec.id === "cad-produtor") {
      const btn = document.createElement("button");
      btn.className = "btn primary";
      btn.textContent = "Salvar produtor";
      btn.addEventListener("click", saveProducer);
      card.appendChild(btn);
    }
    if (currentTab === "cadastro" && sec.id === "cad-profissional") {
      const btn = document.createElement("button");
      btn.className = "btn primary";
      btn.textContent = "Salvar profissional";
      btn.addEventListener("click", saveProfessional);
      card.appendChild(btn);
    }

    page.appendChild(card);
  });
}

function renderSobre() {
  const card = document.createElement("section");
  card.className = "card";
  card.innerHTML = `
    <h2>O Avicheckpoint</h2>
    <p>Plataforma de monitoramento avícola que integra ciência, tecnologia e boas práticas de manejo, com foco em produtividade, bem-estar animal e sustentabilidade.</p>
    <div class="badge">Equipe multidisciplinar</div>
  `;
  page.appendChild(card);

  const team = document.createElement("section");
  team.className = "card";
  team.innerHTML = `<h3>Equipe</h3>`;
  const wrap = document.createElement("div");
  wrap.className = "team";
  const data = [
    {
      nome: "Dayse Helena Lages da Silva",
      cargo: "Médica veterinária, fundadora e orientadora dos materiais",
    },
    {
      nome: "Lorena Stephannie Martins Moreira",
      cargo:
        "Fundadora, estudante de Medicina Veterinária e desenvolvedora dos conteúdos teóricos",
    },
    {
      nome: "João Victor Félix Ribeiro",
      cargo:
        "Fundador, estudante de Medicina Veterinária e desenvolvedor dos conteúdos teóricos",
    },
    {
      nome: "Sanhiag Takaesu",
      cargo:
        "Fundador, estudante de Engenharia de Software e responsável pela parte tecnológica",
    },
    {
      nome: "Brenda Evers",
      cargo:
        "Fundadora, estudante de Engenharia de Software e responsável pela parte tecnológica",
    },
  ];
  data.forEach((m) => {
    const row = document.createElement("div");
    row.className = "member";
    row.innerHTML = `<div style="width:64px;height:64px;border-radius:50%;background:#e5e7eb;"></div>
    <div><div style="font-weight:700">${m.nome}</div><div style="color:var(--muted)">${m.cargo}</div></div>`;
    wrap.appendChild(row);
  });
  team.appendChild(wrap);
  page.appendChild(team);
}

function buildFindingsFromAnswers() {
  const out = {
    panorama: [],
    fortes: [],
    melhorar: [],
    comentarios: [],
    profissao: null,
  };

  const s = valuesState.saude || {};
  if (s.s1 === "Sim") {
    out.panorama.push(
      "Aves com sinais de doença — monitorar e anotar produção/comportamento/mortalidade."
    );
    out.comentarios.push("Sugerimos avaliação de um médico veterinário.");
    out.profissao = out.profissao || "Medicina veterinária";
  } else if (s.s1 === "Não") {
    out.fortes.push(
      "Sem sinais aparentes de doença — continue o monitoramento."
    );
  }
  if (s.s2 && s.s2 !== "Sem alterações") {
    out.melhorar.push(`Sinais clínicos: ${s.s2}.`);
    if (["Espirros"].includes(s.s2))
      out.melhorar.push(
        "Atenção a sinais respiratórios — alta transmissibilidade."
      );
    if (["Diarreia", "Apatia", "Queda na produção"].includes(s.s2))
      out.comentarios.push(
        "Investigar causas multifatoriais com apoio técnico."
      );
    out.profissao = out.profissao || "Medicina veterinária";
  }
  if (s.s3 === "Sim") out.fortes.push("Vacinação em dia.");
  if (s.s3 === "Não") {
    out.melhorar.push("Vacinação não está em dia.");
    out.profissao = out.profissao || "Medicina veterinária";
  }
  if (s.s4 === "Sim") out.fortes.push("Quarentena aplicada para novas aves.");
  if (s.s4 === "Não")
    out.melhorar.push("Implementar quarentena para novas aves.");
  if (s.s5 === "Sim") {
    out.melhorar.push("Contato com aves silvestres — risco sanitário.");
    out.profissao = out.profissao || "Medicina veterinária";
  }
  if (s.s5 === "Não") out.fortes.push("Acesso de aves silvestres restringido.");

  const n = valuesState.nutricao || {};
  if (n.n1 === "Ração industrial")
    out.fortes.push("Uso de ração industrial — dieta balanceada.");
  if (["Comida caseira", "Sobras de alimentos"].includes(n.n1)) {
    out.melhorar.push("Rever dieta — pode faltar nutrientes.");
    out.profissao = out.profissao || "Zootecnia";
  }
  if (n.n2 === "Sim") out.fortes.push("Armazenamento adequado de ração.");
  if (n.n2 === "Não")
    out.melhorar.push(
      "Melhorar armazenamento (local fechado, seco e protegido)."
    );
  if (n.n3 === "Sim")
    out.melhorar.push("Fungos/bolor/insetos na ração — revisar armazenamento.");
  if (n.n3 === "Não") out.fortes.push("Ração sem sinais de contaminação.");

  const a = valuesState.avaliacao || {};
  if (a.a1 === "Sim") out.fortes.push("Registros zootécnicos mantidos.");
  if (a.a1 === "Não")
    out.melhorar.push("Implementar registros (produção, ração, mortalidade).");
  if (a.a2 === "Sim") out.fortes.push("Manejo de luz adequado (fotoperíodo).");
  if (a.a2 === "Não") {
    out.melhorar.push("Ajustar fotoperíodo (~16h luz).");
    out.profissao = out.profissao || "Medicina veterinária";
  }
  if (a.a3 === "Sem alteração e uniforme")
    out.fortes.push("Casca uniforme e sem alterações.");
  if (a.a3 === "Rachaduras/fissuras/manchas") {
    out.melhorar.push(
      "Rachaduras/manchas — revisar coleta, nutrição e manejo."
    );
    out.profissao = out.profissao || "Medicina veterinária";
  }
  if (a.a3 === "Rugosa/com relevo") {
    out.melhorar.push(
      "Casca rugosa/relevo — pode indicar deficiência nutricional/estresse térmico/doenças."
    );
    out.profissao = out.profissao || "Medicina veterinária";
  }
  if (a.a4 === "Sem alteração")
    out.fortes.push("Coleta correta (sem sujidades).");
  if (a.a4 === "Presença de sujidades/fezes")
    out.melhorar.push(
      "Sujidades — aumentar frequência de coleta e higiene de ninhos."
    );
  if (a.a5)
    out.panorama.push(`Preferência de coloração da gema (escala): ${a.a5}.`);

  const d = valuesState.doencas || {};
  const redFlags = ["d1", "d2", "d3", "d4", "d5", "d6", "d7"].filter(
    (k) => d[k] === "Sim"
  ).length;
  if (redFlags >= 1) {
    out.panorama.push(
      "Sinais compatíveis com Influenza Aviária/Newcastle — atenção!"
    );
    out.comentarios.push("Sugerimos avaliação de um médico veterinário.");
    out.profissao = out.profissao || "Medicina veterinária";
  }

  if (out.profissao === "Zootecnia")
    out.comentarios.push("Sugerimos avaliação de um zootecnista.");
  if (out.profissao === "Medicina")
    out.comentarios.push(
      "Sugerimos avaliação de um Médico para colaboradores com sinais clínicos."
    );

  return out;
}

function renderResultado() {
  const wrap = document.createElement("div");
  const title = document.createElement("h2");
  title.textContent = "Relatório — Diagnóstico Automático";
  wrap.appendChild(title);

  const s = buildFindingsFromAnswers();
  const cards = [
    ["Panorama geral", s.panorama, "color-info"],
    ["Pontos fortes da propriedade", s.fortes, "color-ok"],
    ["Pontos que podem melhorar", s.melhorar, "color-warn"],
    ["Comentário", s.comentarios, "color-note"],
  ];

  cards.forEach(([t, list, cls]) => {
    const c = document.createElement("section");
    c.className = `card ${cls}`;
    const h = document.createElement("h3");
    h.textContent = t;
    c.appendChild(h);
    if (!list.length) {
      c.appendChild(document.createTextNode("Sem dados suficientes."));
    } else {
      const ul = document.createElement("ul");
      list.forEach((item) => {
        const li = document.createElement("li");
        li.textContent = item;
        ul.appendChild(li);
      });
      c.appendChild(ul);
    }
    wrap.appendChild(c);
  });

  const prosCard = document.createElement("section");
  prosCard.className = "card";
  const h2 = document.createElement("h3");
  h2.textContent = "Profissionais próximos de você";
  prosCard.appendChild(h2);
  const list = filterProfessionalsSuggestion(s.profissao);
  if (list.length) {
    const ul = document.createElement("ul");
    list.forEach((p) => {
      const li = document.createElement("li");
      li.innerHTML = `<strong>${p.nome}</strong> — ${p.formacao} · ${p.uf}/${
        p.cidade
      }<br/><small>${p.bio || ""}</small>`;
      ul.appendChild(li);
    });
    prosCard.appendChild(ul);
  } else {
    prosCard.appendChild(
      document.createTextNode("Nenhum profissional cadastrado na sua UF ainda.")
    );
  }
  wrap.appendChild(prosCard);

  const feedback = document.createElement("section");
  feedback.className = "card";
  feedback.innerHTML = `<h3>Avalie o site</h3>`;
  const stars = document.createElement("div");
  stars.className = "stars";
  for (let i = 1; i <= 5; i++) {
    const s = document.createElement("span");
    s.textContent = "★";
    s.dataset.v = i;
    s.addEventListener("click", () => {
      [...stars.children].forEach((el) =>
        el.classList.toggle("on", +el.dataset.v <= i)
      );
      localStorage.setItem("rating", i);
    });
    stars.appendChild(s);
  }
  const txt = document.createElement("textarea");
  txt.placeholder = "Deixe um comentário…";
  const save = document.createElement("button");
  save.className = "btn primary";
  save.textContent = "Enviar";
  save.addEventListener("click", () => {
    localStorage.setItem("rating_comment", txt.value);
    alert("Obrigado pelo feedback!");
  });
  feedback.append(stars, txt, document.createElement("br"), save);
  wrap.appendChild(feedback);

  page.appendChild(wrap);
}

function renderSabia() {
  const card = document.createElement("section");
  card.className = "card";
  card.innerHTML = `
    <h2>Você sabia?</h2>
    <ul>
      <li><strong>Manejo dos ovos:</strong> Evite lavar os ovos para não remover a película protetora.</li>
      <li><strong>Manejo da luz:</strong> Fotoperíodo adequado (≈16 horas de luz) melhora a postura.</li>
      <li><strong>Influenza Aviária / Newcastle:</strong> quarentena e barreiras sanitárias reduzem riscos.</li>
    </ul>
    <p>Cartilhas e materiais: <a href="https://www.canva.com/design/DAG0fxSOyyk/QHQvvvoiKL185X4W7G7I_A/" target="_blank" rel="noopener">ver conteúdo</a></p>
  `;
  page.appendChild(card);
}

function boot() {
  render();
  updateNavButtons();
  setActivePill(currentTab);

  document.addEventListener("input", (e) => {
    if (e.target.id === "p_senha" || e.target.id === "pr_senha") {
      const ok = validatePassword(e.target.value);
      e.target.style.borderColor = ok ? "#22c55e" : "#ef4444";
    }
  });
}
boot();
