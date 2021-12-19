package lab11;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            String[] commandSplit = command.split(" ");

            switch (commandSplit[0]) {
                case "/addPerson": {
                    session = factory.getCurrentSession();
                    session.beginTransaction();

                    Customer currentCustomer = IsCurrent(session.createQuery("from Customer").getResultList(), commandSplit[1]);

                    if (currentCustomer == null) {
                        Customer customer = new Customer(commandSplit[1]);
                        session.save(customer);
                    } else {
                        System.out.println(currentCustomer.getName() + " уже есть в базе данных");
                    }

                    session.getTransaction().commit();
                    break;
                }
                case "/showProductsByPerson": {
                    session = factory.getCurrentSession();
                    session.beginTransaction();

                    Customer currentCustomer = IsCurrent(session.createQuery("from Customer").getResultList(), commandSplit[1]);

                    if (currentCustomer != null) {
                        if (currentCustomer.getProducts().size() != 0) {
                            System.out.println("Список покупок " + currentCustomer.getName() + ": ");
                            for (Product product : currentCustomer.getProducts()) {
                                System.out.print(product + ", ");
                            }
                        } else {
                            System.out.println("Список покупок " + currentCustomer.getName() + " пуст.");
                        }

                    } else {
                        System.out.println("Такого покупателя нет");
                    }

                    session.getTransaction().commit();
                    break;
                }
                case "/findPersonsByProductTitle": {
                    session = factory.getCurrentSession();
                    session.beginTransaction();

                    ArrayList<Customer> customersList = new ArrayList<>();
                    List<Customer> customers = session.createQuery("from Customer").getResultList();

                    for (Customer customer : customers) {
                        for (Product product : customer.getProducts()) {
                            if (product.getName().equals(commandSplit[1])) {
                                customersList.add(customer);
                            }
                        }
                    }

                    if (customersList.size() != 0) {
                        System.out.println("Покупатели которые покупали " + commandSplit[1] + ": ");
                        for (Customer customer : customersList) {
                            System.out.print(customer.getName() + "\n");
                        }
                    } else {
                        System.out.println("Покупателей которые покупали " + commandSplit[1] + " нет.");
                    }

                    session.getTransaction().commit();
                    break;
                }
                case "/removePerson": {
                    session = factory.getCurrentSession();
                    session.beginTransaction();

                    Customer currentCustomer = IsCurrent(session.createQuery("from Customer").getResultList(), commandSplit[1]);

                    if (currentCustomer != null) {
                        session.delete(currentCustomer);
                        System.out.println(commandSplit[1] + " - удален");
                    } else {
                        System.out.println("Такого покупателя нет");
                    }

                    session.getTransaction().commit();
                    break;
                }
                case "/removeProduct": {
                    session = factory.getCurrentSession();
                    session.beginTransaction();

                    Product currentProduct = IsCurrentProduct(session.createQuery("from Customer").getResultList(), commandSplit[1]);

                    if (currentProduct != null) {
                        session.delete(currentProduct);
                        System.out.println(commandSplit[1] + " - удалено");
                    } else {
                        System.out.println("Такого товара нет");
                    }

                    session.getTransaction().commit();
                    break;
                }
                case "/buy": {
                    session = factory.getCurrentSession();
                    session.beginTransaction();

                    Customer currentCustomer = IsCurrent(session.createQuery("from Customer").getResultList(), commandSplit[1]);

                    if (currentCustomer != null) {
                        Product product = new Product(commandSplit[2], Integer.parseInt(commandSplit[3]), currentCustomer);
                        currentCustomer.AddProduct(product);
                        session.save(currentCustomer);
                        session.save(product);
                    } else {
                        System.out.println(commandSplit[1] + " нет в базе данных");
                    }

                    session.getTransaction().commit();
                    break;
                }
            }
        }
    }

    public static Customer IsCurrent(List<Customer> customers, String name) {
        Customer currentCustomer = null;
        for (Customer customer : customers) {
            if (customer.getName().equals(name)) {
                currentCustomer = customer;
            }
        }
        return currentCustomer;
    }

    public static Product IsCurrentProduct(List<Product> products, String name) {
        Product currentProduct = null;
        for (Product product : products) {
            if (product.getName().equals(name)) {
                currentProduct = product;
            }
        }
        return currentProduct;
    }
}
