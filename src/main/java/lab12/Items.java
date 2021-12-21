package lab12;

import org.hibernate.annotations.OptimisticLock;

import javax.persistence.*;

@Entity
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OptimisticLock(excluded = true)
    private int val;

    @Version
    private Long version;

    public void Val() {
        this.val++;
    }
}
