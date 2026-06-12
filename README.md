# 🚭 Pare de Fumar — Contador de Progresso

Aplicação desktop desenvolvida em *Java* com interface gráfica *Swing* para ajudar pessoas a pararem de fumar, exibindo quantos dias estão sem fumar, quantos cigarros não foram fumados e o dinheiro economizado.

---

## 📸 Funcionalidades

- ✅ Contagem de dias sem fumar
- 🚬 Total de cigarros não fumados
- 💰 Cálculo do dinheiro economizado
- 💪 Mensagens motivacionais de acordo com o progresso
- ⚠️ Validação robusta de todos os campos com feedback ao usuário

---

## 🗂️ Estrutura do Projeto


QuitSmokingApp/
├── Main.java
├── exception/
│   └── DadosInvalidosException.java
├── model/
│   ├── Usuario.java
│   └── Fumante.java
├── service/
│   └── FumanteService.java
└── ui/
    ├── JanelaPrincipal.java
    └── PainelResultado.java


---

## 🧱 Conceitos de POO Aplicados

| Conceito | Onde é aplicado |
|---|---|
| *Herança* | Fumante extends Usuario, JanelaPrincipal extends JFrame, PainelResultado extends JPanel |
| *Encapsulamento* | Atributos private com getters/setters validados em todas as classes de modelo |
| *Polimorfismo* | Método getResumo() definido em Usuario e sobrescrito em Fumante |
| *Tratamento de Erros* | DadosInvalidosException (unchecked), throw nos setters, try/catch na UI |

---

## ⚙️ Requisitos

- Java *11* ou superior
- Nenhuma dependência externa — usa apenas bibliotecas padrão do JDK (javax.swing, java.time)

---

## ▶️ Como Executar

*1. Clone o repositório*
bash
git clone https://github.com/seu-usuario/QuitSmokingApp.git
cd QuitSmokingApp


*2. Compile*
bash
javac -d out $(find . -name "*.java")


> No Windows (CMD):
> cmd
> javac -d out exception\*.java model\*.java service\*.java ui\*.java Main.java
> 

*3. Execute*
bash
java -cp out Main


---

## 📋 Como Usar

1. Preencha seu *nome* e *data de nascimento*
2. Informe quantos *cigarros fumava por dia*
3. Informe o *preço do maço* e a *quantidade de cigarros por maço*
4. Informe a *data em que parou de fumar* (formato dd/MM/yyyy)
5. Clique em *✅ Calcular Progresso*

---

## 📁 Descrição das Classes

- *Main* — ponto de entrada; inicializa a UI na Event Dispatch Thread do Swing
- *Usuario* — classe base com nome e data de nascimento; define o método polimórfico getResumo()
- *Fumante* — herda de Usuario; contém a lógica de cálculo de dias, cigarros e dinheiro economizado
- *DadosInvalidosException* — exceção unchecked personalizada com campo inválido identificado
- *FumanteService* — converte e valida os dados brutos da UI antes de criar um Fumante
- *JanelaPrincipal* — janela principal (herda de JFrame) com formulário e botões
- *PainelResultado* — painel de exibição (herda de JPanel) com resultados e mensagem motivacional

---

## 📄 Licença

Este projeto é de uso livre para fins educacionais.
