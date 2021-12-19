package lab11;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany (
        mappedBy = "customer",
        cascade = CascadeType.ALL
    )
    private List<Product> products;

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void AddProduct(Product product) {
        products.add(product);
    }

    public Customer() {
    }

    public Customer(String name) {
        this.name = name;
    }
}
