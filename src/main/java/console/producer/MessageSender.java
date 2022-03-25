package console.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import console.dictionary.Queue;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class MessageSender {
    private final static String HOST_NAME = "localhost";
    private final static String EXCHANGER_NAME = "program-language";

    public static void main(String[] args) throws Exception {
        Scanner scanner;
        Optional<Queue> queue;

        String enterString = "";
        String message;
        String[] pairKeyAndMessage = new String[2];

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST_NAME);

        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()
        ) {
            channel.exchangeDeclare(EXCHANGER_NAME, BuiltinExchangeType.DIRECT);

            for (Queue q : Queue.values()) {
                channel.queueDeclare(q.getName(), false, false, false, null);
                channel.queueBind(q.getName(), EXCHANGER_NAME, q.getKey());
            }

            scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Сообщение: ");
                enterString = scanner.nextLine();

                if (enterString.equals("exit")) {
                    break;
                }

                pairKeyAndMessage = enterString.split(" ", 2);
                String key = pairKeyAndMessage[0];
                message = pairKeyAndMessage[1];

                queue = Arrays.asList(Queue.values()).stream()
                        .filter(q -> q.getKey().equals(key))
                        .findFirst();

                if (queue.isPresent()) {
                    channel.basicPublish(EXCHANGER_NAME, queue.get().getKey(), null, message.getBytes());
                    System.out.println(" sent > key: " + queue.get().getKey() + ", message: '" + message + "'");
                } else {
                    System.out.println("Ошибка! Указанная тема не существует.");
                }
            }

            scanner.close();
        }
    }
}
