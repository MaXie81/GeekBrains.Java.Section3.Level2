package console.consumer;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import console.dictionary.Queue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class MessageReceiver {
    private final static String HOST_NAME = "localhost";

    public static void main(String[] argv) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Optional<Queue> queue;

        while (true) {
            System.out.print("Тема сообщений: ");
            String key = scanner.nextLine();

            queue = Arrays.asList(Queue.values()).stream()
                    .filter(q -> q.getKey().equals(key))
                    .findFirst();

            if (queue.isPresent()) {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost(HOST_NAME);

                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();

                channel.queueDeclare(queue.get().getName(), false, false, false, null);
                System.out.println("Ожидание получения сообщения...");

                DeliverCallback deliverCallback = new DeliverCallback() {
                    @Override
                    public void handle(String consumerTag, Delivery delivery) throws IOException {
                        String message = new String(delivery.getBody(), "UTF-8");
                        System.out.println(" received < '" + message + "'");
                    }
                };

                channel.basicConsume(queue.get().getName(), true, deliverCallback, new CancelCallback() {
                    @Override
                    public void handle(String consumerTag) throws IOException {}
                });

                break;
            } else {
                System.out.println("Ошибка! Указанная тема не существует.");
            }
        }
        scanner.close();
    }
}
