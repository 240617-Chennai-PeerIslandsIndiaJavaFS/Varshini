import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

abstract class Creature {
    protected String name;
    protected int health;

    public Creature(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public abstract void attack(Creature target);
    public abstract void takeDamage(int damage);
}

interface Interactable {
    void interact(Player player);
}

class Player extends Creature implements Interactable {
    private int damage;
    private List<Item> inventory;

    public Player(String name, int health, int damage) {
        super(name, health);
        this.damage = damage;
        this.inventory = new ArrayList<>();
    }

    @Override
    public void attack(Creature target) {
        System.out.println(name + " attacks " + target.getName() + " for " + damage + " damage.");
        target.takeDamage(damage);
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        System.out.println(name + " takes " + damage + " damage. Health is now " + health + ".");
    }

    @Override
    public void interact(Player player) {
        // Player-specific interaction logic can go here.
    }

    public void pickUpItem(Item item) {
        inventory.add(item);
        System.out.println(name + " picks up " + item.getName() + ".");
    }

    public void useItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equals(itemName)) {
                item.use(this);
                inventory.remove(item);
                break;
            }
        }
    }
}

class Goblin extends Creature implements Interactable {
    public Goblin(String name, int health) {
        super(name, health);
    }

    @Override
    public void attack(Creature target) {
        int damage = 5;
        System.out.println(name + " attacks " + target.getName() + " for " + damage + " damage.");
        target.takeDamage(damage);
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        System.out.println(name + " takes " + damage + " damage. Health is now " + health + ".");
    }

    @Override
    public void interact(Player player) {
        System.out.println(name + " encounters " + player.getName() + "!");
        attack(player);
    }
}

class Dragon extends Creature implements Interactable {
    public Dragon(String name, int health) {
        super(name, health);
    }

    @Override
    public void attack(Creature target) {
        int damage = 15;
        System.out.println(name + " breathes fire on " + target.getName() + " for " + damage + " damage.");
        target.takeDamage(damage);
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        System.out.println(name + " takes " + damage + " damage. Health is now " + health + ".");
    }

    @Override
    public void interact(Player player) {
        System.out.println(name + " encounters " + player.getName() + "!");
        attack(player);
    }
}

abstract class Item {
    protected String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void use(Player player);
}

class Potion extends Item {
    private int healAmount;

    public Potion(String name, int healAmount) {
        super(name);
        this.healAmount = healAmount;
    }

    @Override
    public void use(Player player) {
        player.health += healAmount;
        System.out.println(player.getName() + " uses " + name + " and heals " + healAmount + " health. Health is now " + player.getHealth() + ".");
    }
}

class Weapon extends Item {
    private int extraDamage;

    public Weapon(String name, int extraDamage) {
        super(name);
        this.extraDamage = extraDamage;
    }

    @Override
    public void use(Player player) {
        // Example: Increase player's attack damage
        // In a real implementation, this would likely involve modifying the player's damage property
        System.out.println(player.getName() + " uses " + name + " and gains " + extraDamage + " extra damage.");
    }
}

public class DungeonAdvanture {
    private static final Random RANDOM = new Random();
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        Player player = new Player("Hero", 100, 10);
        List<Creature> creatures = new ArrayList<>();
        creatures.add(new Goblin("Goblin", 30));
        creatures.add(new Dragon("Dragon", 50));
        List<Item> items = new ArrayList<>();
        items.add(new Potion("Health Potion", 20));
        items.add(new Weapon("Sword", 10));

        boolean gameOver = false;
        while (!gameOver && player.getHealth() > 0) {
            System.out.println("You enter a new room...");
            int encounter = RANDOM.nextInt(2);

            if (encounter == 0 && !creatures.isEmpty()) {
                Creature creature = creatures.remove(RANDOM.nextInt(creatures.size()));
                if (creature instanceof Interactable) {
                    ((Interactable) creature).interact(player);
                }
                while (creature.getHealth() > 0 && player.getHealth() > 0) {
                    player.attack(creature);
                    if (creature.getHealth() > 0) {
                        creature.attack(player);
                    }
                }
                if (player.getHealth() <= 0) {
                    gameOver = true;
                    System.out.println("You have been defeated by " + creature.getName() + "!");
                } else {
                    System.out.println("You have defeated " + creature.getName() + "!");
                }
            } else if (!items.isEmpty()) {
                Item item = items.remove(RANDOM.nextInt(items.size()));
                player.pickUpItem(item);
            }

            if (player.getHealth() > 0) {
                System.out.println("Do you want to use an item? (yes/no)");
                if (SCANNER.nextLine().equalsIgnoreCase("yes")) {
                    System.out.println("Enter the item name:");
                    String itemName = SCANNER.nextLine();
                    player.useItem(itemName);
                }

                System.out.println("Do you want to continue exploring? (yes/no)");
                if (SCANNER.nextLine().equalsIgnoreCase("no")) {
                    gameOver = true;
                    System.out.println("You have exited the dungeon. Game Over.");
                }
            }
        }

        if (player.getHealth() <= 0) {
            System.out.println("You have been defeated. Game Over.");
        } else {
            System.out.println("Congratulations! You survived the dungeon.");
        }
    }
}
