public class Product {
    private String name;//nome do produto
    private double weight;//peso do produto
    private int quantity;//quantidade de produtos

    //construtor
    public Product(String name, double weight, int quantity) {
        this.name = name;
        this.weight = weight;
        this.quantity = quantity;
    }

    //construtor de cópia
    public Product(Product product) {
        this.name = product.name;
        this.weight = product.weight;
        this.quantity = product.quantity;
    }

    //getters e setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    //método para imprimir os dados do produto
    public String toString() {

        return "\n" + name + " - " + quantity + " unidades";
    }
}
