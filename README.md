# ChatApp - Aplicação de Chat Multiusuário com Comunicação Multicast

Este projeto é uma aplicação de chat em Java que utiliza comunicação multicast para permitir a interação entre múltiplos usuários em diferentes salas de bate-papo. Cada sala é identificada por um endereço IP multicast distinto, garantindo que as mensagens sejam propagadas para todos os participantes da sala.

## Requisitos

- Java Development Kit (JDK) 8 ou superior
- Um terminal ou prompt de comando

## Funcionalidades

- **Salas de Chat:** Cada sala de bate-papo possui um endereço IP multicast único (por exemplo, `230.0.0.1` para Sala 1).
- **Mensagens de Entrada e Saída:** Ao entrar em uma sala, o usuário envia uma mensagem anunciando sua entrada. Ao sair, uma mensagem de despedida é enviada.
- **Propagação de Mensagens:** Todas as mensagens enviadas para a sala são propagadas para todos os membros da sala.
- **Comunicação Assíncrona:** O aplicativo utiliza threads separadas para enviar e receber mensagens, permitindo que os usuários interajam em tempo real.

## Especificações Técnicas

- **Endereços Multicast:** A aplicação utiliza endereços multicast específicos para cada sala:
  - Sala 1: `230.0.0.1`
  - Sala 2: `230.0.0.2`
  - Sala 3: `230.0.0.3`
- **Métodos Implementados:**
  - `boolean enterRoom(String groupAddress)`: Entra na sala e retorna `true` se o ingresso foi bem-sucedido, ou `false` em caso de falha.
  - `boolean leaveRoom()`: Remove o usuário da sala e retorna `true` se a saída foi bem-sucedida, ou `false` em caso de falha.
  - `void sendMessage(String message)`: Envia uma mensagem para a sala.
  - `void receiveMessages()`: Recebe mensagens dos outros usuários na sala.

## Instalação

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/seu-usuario/chatapp.git
   cd chatapp
   ```

2. **Compile o código:**

   Navegue até o diretório onde o arquivo `ChatApp.java` está localizado e execute o comando abaixo para compilar o código:

   ```bash
   javac ChatApp.java
   ```

## Como Utilizar

1. **Executando a aplicação:**

   Após compilar o código, execute o aplicativo utilizando o comando abaixo:

   ```bash
   java ChatApp <Nome de Usuario> <Endereço Multicast>
   ```

   Exemplo:

   ```bash
   java ChatApp "Carlos" 230.0.0.1
   ```

   Isso fará com que o usuário "Carlos" entre na Sala 1 com o endereço multicast `230.0.0.1`.

2. **Enviando mensagens:**

   Após entrar na sala, você pode digitar suas mensagens diretamente no terminal. Elas serão enviadas para todos os usuários conectados ao mesmo endereço multicast.

3. **Saindo da sala:**

   Para sair da sala, digite o comando `/sair` e pressione Enter. Isso enviará uma mensagem de despedida para todos na sala e fechará a aplicação.

## Testando o Software

Para testar a aplicação:

1. Abra dois ou mais terminais.
2. Execute o comando `java ChatApp <Nome de Usuario> <Endereço Multicast>` em cada terminal, substituindo `<Nome de Usuario>` por nomes diferentes e usando o mesmo `<Endereço Multicast>` (por exemplo, `230.0.0.1`).
3. Envie mensagens de um terminal e veja as mensagens aparecendo nos outros terminais.
4. Teste a funcionalidade de entrada e saída de sala observando as mensagens correspondentes.

## Interação e Debug

Se você encontrar algum problema:

- Verifique se o endereço multicast utilizado é válido e está disponível.
- Verifique se a porta `6789` não está sendo usada por outro processo.
- Certifique-se de que sua interface de rede está ativa e sem problemas de conectividade.
- Certifique-se de que a aplicação está tratando corretamente exceções de rede e I/O, especialmente ao ingressar/sair de grupos multicast.


