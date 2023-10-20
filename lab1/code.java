import java.lang.Math;
import java.util.Set;
public class Hello {
    // функция для извлечения корня n-й степени
    static double sqrt (double x, double n){ 
        return Math.pow (x, 1/n); 
    } 
    public static void main(String[] args) {
        // #1
        // создание массива для задания 1
        long[] c = new long[10];
        // заполнение массива числами от 6 до 15 включительно
        for(int i = 0; i < 10; i++){
           c[i] = i + 6;
        }
        // #2
        // создание массива для задания 2
        float[] x = new float[11];
        // заполнение массива случайными числами в диапазоне от -12.0 до 8.0
        for(int i = 0; i < 11; i++){
            x[i] = (float)Math.random() * 20 - 12;
        }
        // #3
        // создание двумерного массива для задания 3
        float [][] c1 = new float[10][11];
        // создание Set для проверки выполнения 2-го условия в 3 задании
        Set<Long> sec_condition = Set.of(7L, 8L, 9L, 10L, 13L);
        // заполнение массива по заданному условию
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 11; j++){
                if(c[i] == 12) {
                    c1[i][j] = (float)Math.tan((float)Math.exp(Math.abs(x[j])));
                }
                else if(sec_condition.contains(c[i])){
                    c1[i][j] = (float)sqrt((float)Math.cos(x[j])/2, 3);
                }
                else{
                    c1[i][j] = (float)(3 * (float)Math.cos((float)Math.atan((x[j]-2)/2)* (float)Math.exp(1) + 1) / 8);
                }
            }
        }
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 11; j++){
            	 System.out.printf(“%.4f\t”, c1[i][j]);


            }
            System.out.println();
    }
}

