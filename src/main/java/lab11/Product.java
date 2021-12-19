package lab11;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer price;

    @ManyToOne (
            optional=false,
            cascade=CascadeType.ALL
    )
    @JoinColumn (
            name = "customer_id"
    )
    private Customer customer;

    public String getName() {
        return name;
    }

    public Product () {
    }

    public Product(String name, Integer price, Customer customer) {
        this.name = name;
        this.price = price;
        this.customer = customer;
    }
}
