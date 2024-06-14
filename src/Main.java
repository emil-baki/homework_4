import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int medicHeal = 100;
    public static int[] heroesHealth = {290, 270, 250,300,200,190,179};
    public static int[] heroesDamage = {25, 15, 10, 0, 0, 0, 6 };
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic","Medic","Lucky","Witcher","Thor"};
    public static int roundNumber = 0;
    public static boolean luckyAlive = true;
    public static boolean witcherAlive = true;
    public static boolean thorAlive = true;
    public static boolean bossStunned = false;

    public static void main(String[] args) {
        showStatistics();
        while (!isGameOver()) {
            round();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void round() {
        roundNumber++;
        chooseBossDefence();
        if (!bossStunned) {
            bossAttacks();
        }
        bossAttacks();
        heroesAttack();
        showStatistics();
        medicHeal();
        luckyChance();
        witcherRevive();
        thorStunner();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (bossDefence == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = damage * coeff;
                    System.out.println("Critical damage: " + heroesAttackType[i] + " " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void medicHeal (){
        int medic = 3;
        if (heroesHealth[3] <= 0) { // < =меньше
            return;
        }
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && i != medic) {
                heroesHealth[i] = heroesHealth[i] + medicHeal;
                System.out.println("Медик вылечил" + heroesAttackType[i] + " на " + medicHeal + " hp ");
                break;
            }
        }
    }

    public static void luckyChance () {
        if (luckyAlive && bossDamage > 0) {
            Random random = new Random();
            boolean isLucky = random.nextBoolean();
            if (isLucky ){
                System.out.println("Лаки увернулся от ударов босса");
            }

        }
    }
    public static void witcherRevive(){
        if (witcherAlive){
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] == 0 && i !=3) {
                    heroesHealth[i] = 150;
                    witcherAlive = false;
                    System.out.println("Ведьма вернула к жизни " + heroesAttackType[i] +", но умерла, пожертвовав собой");
                    break;
                }
            }
        }
    }
    public static void thorStunner() {
        if (thorAlive && ! bossStunned){
            Random random = new Random();
            boolean isStunned = random.nextBoolean();
            if (isStunned ){
                bossStunned = true;
                System.out.println("Тор заглушил на один раунд");
            }
        }
    }

    public static void bossAttacks() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void showStatistics() {
        System.out.println("ROUND " + roundNumber + " ----------------");
        /*String defence;
        if(bossDefence == null) {
            defence = "None";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "None" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] +
                    " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }
}