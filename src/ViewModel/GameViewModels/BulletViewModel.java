package ViewModel.GameViewModels;

import Model.Domain.Bullet;

public class BulletViewModel {
    private double[] coordinatesOfTheBullet;
    private int[] velocity;
    private Bullet bullet;

    public BulletViewModel(Bullet bullet) {
        this.coordinatesOfTheBullet = bullet.getCoordinates();
        this.velocity = bullet.getVelocity();
        this.bullet = bullet;
    }

    public double[] getCoordinatesOfTheBullet() {
        return coordinatesOfTheBullet;
    }

    public int[] getBulletVelocity() {
        return velocity;
    }

    public Bullet getBullet() {
        return bullet;
    }
}
