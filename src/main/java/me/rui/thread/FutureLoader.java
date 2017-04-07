package me.rui.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by cr on 2017/3/25.
 */
public class FutureLoader {

    public static void main(String args[]) {
        FutureLoader futureLoader = new FutureLoader();
        futureLoader.start();
        try {
            System.out.println(futureLoader.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private final FutureTask<ProductInfo> future = new FutureTask<>( () ->loadProductInfo() );

    private final Thread thread = new Thread(future);

    public void start() {
        thread.start();
    }

    public ProductInfo get() throws InterruptedException{
        try {
            return future.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            cause.printStackTrace();
        }
        return null;
    }


    private ProductInfo loadProductInfo(){
        return new ProductInfo("Product info.");
    }

    static class ProductInfo{
        String info;

        public ProductInfo(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        @Override
        public String toString() {
            return "ProductInfo{" +
                    "info='" + info + '\'' +
                    '}';
        }
    }
}
