import java.util.Scanner;

class SomaArrayParalelo {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int numThreads = 2; // Número de threads (subarrays)

        // Passo 1: Calcular a soma dos elementos em subarrays menores
        int subarraySize = array.length / numThreads;
        Thread[] threads = new Thread[numThreads];
        int[] partialSums = new int[numThreads];

        for (int i = 0; i < numThreads; i++) {
            final int start = i * subarraySize;
            final int end = (i == numThreads - 1) ? array.length : (i + 1) * subarraySize;

            final int threadIndex = i; // Variável efetivamente final

            threads[i] = new Thread(() -> {
                int sum = 0;
                for (int j = start; j < end; j++) {
                    sum += array[j];
                }
                partialSums[threadIndex] = sum; // Usamos threadIndex em vez de i
            });
            threads[i].start();
        }

        // Aguardar a conclusão de todas as threads
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Passo 4: Somar os resultados parciais
        int totalSum = 0;
        for (int partialSum : partialSums) {
            totalSum += partialSum;
        }

        System.out.println("A soma dos elementos do array é: " + totalSum);
    }
}
