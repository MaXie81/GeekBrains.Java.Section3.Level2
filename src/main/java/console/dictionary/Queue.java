package console.dictionary;

public enum Queue {
    JAVA("queue-java", "java"),
    PHP("queue-php", "php"),
    SQL("queue-sql", "sql");

    private String name;
    private String key;

    Queue(String name, String key) {this.name = name; this.key = key;}

    public String getName() {return name;}
    public String getKey() {return key;}
}
