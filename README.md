# ⏰ Sincronização de Relógios - Algoritmo de Berkeley

Este projeto implementa o algoritmo de **Berkeley** para sincronização de relógios em uma rede de computadores, utilizando **Sockets em Java**.

## 📚 Sobre o Algoritmo de Berkeley

O algoritmo de Berkeley é uma técnica de sincronização de tempo **sem servidor com relógio de referência externo**. Ele funciona de forma colaborativa:

1. Um dos nós (o **servidor**) atua como coordenador.
2. Ele **consulta a hora atual de todos os clientes** conectados.
3. Cada cliente retorna a **diferença de tempo** em relação ao servidor.
4. O servidor calcula a **média das diferenças** (ignorando valores extremos, se necessário).
5. O servidor então **envia ajustes individuais** para cada cliente (positivos ou negativos) com base na média, para que todos fiquem sincronizados.

---

## 🧪 Exemplo de Funcionamento

- Horários iniciais:
Cliente 1: 10:30

Cliente 2: 12:00

Servidor: 11:00

🧮 Ajustes:
Cliente 1: +40 minutos → 11:10

Cliente 2: −50 minutos → 11:10

Servidor: +10 minutos → 11:10

✅ Hora final sincronizada:

Resultado:

🕒 Todos os relógios ficam ajustados para 11:10!

---

🛠️ Tecnologias Utilizadas
Java

Sockets (TCP)

API de Data/Hora: java.time.LocalTime

Threads para lidar com múltiplas conexões simultâneas

📌 Observações
A comunicação é feita via localhost (127.0.0.1), ideal para testes locais.

Os relógios dos clientes são simulados com horários aleatórios para fins de demonstração e testes.

O sistema pode ser estendido para redes reais com IPs distintos.

