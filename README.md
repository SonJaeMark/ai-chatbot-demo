Here's a clean and informative `README.md` tailored for your **AI Chatbot Demo** project using Spring Boot + Vaadin:

---

# 🤖 AI Chatbot Demo

This project is a **demo frontend** built with **Spring Boot** and **Vaadin**, designed to interact with an external [AI Chatbot API](https://ai-chatbot-0mmy.onrender.com). It showcases a floating chatbot UI that uses contextual information (like college admissions or movie info) to provide smart, human-like replies.

---

## 🌟 Features

* 💬 Floating chatbot UI (`/chat` route)
* 📄 Preloaded contexts:

  * College admission Q\&A
  * Movie showtimes data
* 🌐 API Integration with external chatbot backend (`/gemini`)
* 🔧 REST calls using Spring's `RestTemplate`
* 🖼️ Vaadin-based web interface
* 🚀 Dockerized build for deployment

---

## 📦 Tech Stack

| Layer      | Tech            |
| ---------- | --------------- |
| Backend    | Spring Boot 3.5 |
| UI         | Vaadin 24       |
| Language   | Java 17         |
| Build Tool | Maven           |
| Container  | Docker          |

---

## 🛠️ Setup & Run

### 🖥️ Local Development (Using Maven)

1. Clone the repo:

   ```bash
   git clone https://github.com/your-username/ai-chatbot-demo.git
   cd ai-chatbot-demo
   ```

2. Run the app:

   ```bash
   ./mvnw spring-boot:run
   ```

3. Visit:

   * Homepage: [http://localhost:8080](http://localhost:8080)
   * Chat UI: [http://localhost:8080/chat](http://localhost:8080/chat)

---

### 🐳 Docker (Build & Run)

```bash
# Build the Docker image
docker build -t ai-chatbot-demo .

# Run the container
docker run -p 8080:8080 ai-chatbot-demo
```

---

## 🧠 How It Works

* On app start, the `/chat` view initializes a session by sending **context + instruction** to the AI Chatbot API.
* The chatbot component is a floating dialog (bottom-right), initially showing a greeting.
* User enters a message → backend sends it to the chatbot → chatbot responds with an emoji-rich plain text reply.
* The chatbot context is **hidden from the user**, making the interaction feel organic.

---

## 📁 Project Structure

```
ai-chatbot-demo/
├── src/main/java
│   ├── .../config                  # Vaadin shell config
│   ├── .../service                 # Provides context/instruction
│   ├── .../views                   # MainView & ChatBoxView (UI)
│   └── AiChatbotDemoApplication    # Entry point
├── src/main/resources
│   └── application.properties      # App name, Vaadin launch config
├── Dockerfile                      # Multi-stage build (JDK + Redis CLI)
├── pom.xml                         # Maven dependencies
└── README.md                       # (This file)
```

---

## 🔗 API Used

This project integrates with a **public AI Chatbot API**:

```
POST /gemini/upload-context
POST /gemini/chat
```

> See [ai-chatbot API GitHub repo](https://github.com/sonjaemark/ai-chatbot) for more details.

---

## 🧪 Example Prompts

> Try questions like:

* "What do I need for college applications?"
* "What's the ticket price of The Galactic Escape?"
* "Should I submit SAT scores if it's test-optional?"

The bot replies in messenger-style text with emojis.

---

## ✍️ Author

**Mark Sonjae**
[GitHub](https://github.com/sonjaemark)

---

## 📜 License

This is a demo project and is provided **as-is** for educational purposes.

---

