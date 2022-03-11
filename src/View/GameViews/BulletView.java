package View.GameViews;

import ViewModel.GameViewModels.BulletViewModel;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class BulletView {
    private Circle bulletView;
    private int[] velocity;

    public BulletView(BulletViewModel bulletViewModel) {
        int radius = bulletViewModel.getBullet().getSize();
        String color = bulletViewModel.getBullet().getColor();

        bulletView = new Circle(radius, Paint.valueOf(color));

        bulletView.setTranslateX(bulletViewModel.getCoordinatesOfTheBullet()[0]);
        bulletView.setTranslateY(bulletViewModel.getCoordinatesOfTheBullet()[1]);
        velocity = bulletViewModel.getBulletVelocity();
    }

    public void update() {
        bulletView.setTranslateX(bulletView.getTranslateX() + velocity[0]);
        bulletView.setTranslateY(bulletView.getTranslateY() + velocity[1]);
    }

    public double[] getCoordinates() {
        return new double[]{bulletView.getTranslateX(), bulletView.getTranslateY()};
    }

    public Circle getBulletView() {
        return bulletView;
    }
}
