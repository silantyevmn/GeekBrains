package ru.silantyevmn.java_core_professinal.lesson5;

/**
 * ru.silantyevmn.java_core_professinal.lesson5
 * Created by Михаил Силантьев on 01.12.2017.
 */
class Tunnel extends Stage {
    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                MainClass.SEMAPHORE_TUNNEL.acquire(); // въезд в туннель заблокирует, пока семафор не разрешит доступ
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                MainClass.SEMAPHORE_TUNNEL.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
