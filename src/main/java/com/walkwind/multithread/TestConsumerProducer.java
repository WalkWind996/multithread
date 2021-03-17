package com.walkwind.multithread;

/**
 * @Program: multithread
 * @ClassName: Product
 * @Description:
 * @Author: 邢风
 * @Version:
 * @Create: 2021-03-17 17:02
 **/
public class TestConsumerProducer {
    public static void main(String[] args) {

        Product product = new Product();
        Consumer consumer = new Consumer();
        consumer.setProduct(product);
        Producer producer = new Producer();
        producer.setProduct(product);


        Thread thread = new Thread(producer);
        Thread thread1 = new Thread(consumer);
        thread.start();
        thread1.start();

    }

}

/*消费者*/
class Consumer implements Runnable {
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                product.subProduct();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


/*生产者*/
class Producer implements Runnable {
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                product.addProduct();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Product {
    private int count = 10;

    public synchronized void addProduct() throws InterruptedException {
        while (count >= 1) {
            this.notifyAll();
        }
        this.wait();
        count = count + 1;
        System.out.println("开始生产货物剩余" + count);
    }

    public synchronized void subProduct() throws InterruptedException {
        while (count <= 0) {
            this.wait();
        }
        count = count - 1;
        System.out.println("开始消费货物剩余" + count );
        this.notifyAll();
    }
}