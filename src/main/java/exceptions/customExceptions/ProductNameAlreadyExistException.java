package exceptions.customExceptions;

public class ProductNameAlreadyExistException extends RuntimeException {

    public ProductNameAlreadyExistException(String productName) {
        super("Product with name '" + productName + "' already exists.");
    }
}
