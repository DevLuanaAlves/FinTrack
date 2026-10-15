# 💰 FinTrack

Aplicação desktop em Java para **controle de finanças pessoais**: registre receitas e despesas, acompanhe o histórico de transações e visualize o resumo financeiro em um painel.

Projeto desenvolvido no programa **Capacita Java**.

## ✨ Funcionalidades

- Cadastro de transações (receitas e despesas)
- Listagem e acompanhamento das transações
- Dashboard com o resumo financeiro
- Transações mensais
- Persistência dos dados em banco local
- Validação de entradas e tratamento de exceções

<!-- AJUSTAR: remova ou acrescente funcionalidades conforme o que o app realmente faz -->

## 🛠️ Tecnologias

- **Java** (JDK 17 ou superior) <!-- AJUSTAR: versão usada -->
- **JavaFX** para a interface gráfica (FXML + CSS)
- **SQLite** como banco de dados local <!-- AJUSTAR: confirme o banco -->
- **JUnit** para testes automatizados <!-- AJUSTAR: confirme a versão -->
- **Apache Ant / NetBeans** para build

## 📁 Estrutura do projeto

```
FinTrack/
├── src/
│   ├── app/          # Classes de entrada e telas principais da aplicação
│   ├── controller/   # Controllers JavaFX, arquivos .fxml e CSS
│   ├── model/        # Entidades (Transacao, TransacaoMensal)
│   ├── DAO/          # Acesso a dados (TransacaoDAO)
│   ├── database/     # Conexão e inicialização do banco
│   └── repository/   # Repositório genérico
├── test/             # Testes automatizados
├── build.xml         # Script de build (Ant)
└── nbproject/        # Configurações do projeto NetBeans
```

## 🚀 Como executar

### Pré-requisitos

- JDK 17 ou superior instalado
- JavaFX SDK configurado <!-- AJUSTAR -->
- NetBeans (recomendado)

### Passo a passo

1. Clone o repositório:

   ```bash
   git clone https://github.com/DevLuanaAlves/FinTrack.git
   ```

2. Abra a pasta `FinTrack` no **NetBeans** (*File → Open Project*).
3. Verifique se as bibliotecas (JavaFX e driver do banco) estão adicionadas ao projeto.
4. Execute a classe `app.MainApplication`.

O arquivo do banco (`fintrack.db`) é criado automaticamente na primeira execução.

## 🧪 Testes

Os testes ficam na pasta `test/` e cobrem model, DAO, controller e repositório. Para rodar, use o NetBeans (*Run → Test Project*) ou:

```bash
ant test
```

## 📸 Screenshots

<!-- AJUSTAR: adicione prints das telas, por exemplo: ![Dashboard]()
 -->



## 👩‍💻 Autora

**Luana Alves**
GitHub: [@DevLuanaAlves](https://github.com/DevLuanaAlves)
