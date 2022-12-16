package customer.dataaccess.mongo;

import customer.application.Customer;
import customer.dataaccess.components.CustomerRepository;
import java.util.ArrayList;
import shared.dataaccess.mongo.MongoRepository;

/**
 *
 * @author albertosml
 */
public class MongoCustomerRepository extends MongoRepository implements CustomerRepository {

    @Override
    public int register(Customer customer) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<String> getTinList() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
