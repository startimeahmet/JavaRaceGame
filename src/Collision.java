
public class Collision {
    public int car_width, car_height, obj_width, obj_height, player_x, player_y, random_x, random_y, randomX_var,randomY_var;

    public Collision(Car playerCar, Random_Car pc_object) {

        car_width = playerCar.carImg.getWidth() - 10;
        car_height = playerCar.carImg.getHeight() - 10;

        obj_width = pc_object.carImg.getWidth() - 10;
        obj_height = pc_object.carImg.getHeight() - 10;

        player_x = playerCar.getX();
        player_y = playerCar.getY();

        random_x = pc_object.getX();
        random_y = pc_object.getY();
    }

    public boolean isCollision() {
        for (int x = 0; x < car_width; x++) {
            for (int y = 0; y < car_height; y++)
            {
                randomX_var = x;
                randomY_var = y;
                if (x > obj_width)
                {
                    randomX_var = obj_width;
                }
                if (y > obj_height)
                {
                    randomY_var = obj_height;
                }
                if ((randomX_var + random_x == player_x && randomY_var + random_y == player_y) ||
                        (x + player_x == random_x && y + player_y == random_y) ||
                        (randomX_var + random_x == player_x && random_y == player_y + y) ||
                        (x + player_x == random_x && randomY_var + random_y == player_y))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
