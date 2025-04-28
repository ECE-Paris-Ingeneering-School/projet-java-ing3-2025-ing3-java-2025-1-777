package model;

import java.util.Objects;

/**
 * Classe qui représente une ligne de panier persistée
 * userId, articleId, taille choisie, quantité.
 */
public class CartItem {
    private int userId;
    private int articleId;
    private String size;
    private int quantity;
    private Article article;


    /** Constructeur complet */
    public CartItem(int userId, int articleId, String size, int quantity) {
        this.userId    = userId;
        this.articleId = articleId;
        this.size      = size;
        this.quantity  = quantity;
        this.article = article;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getArticleId() {
        return articleId;
    }
    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem)) return false;
        CartItem that = (CartItem) o;
        return userId == that.userId
                && articleId == that.articleId
                && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, articleId, size);
    }

    @Override
    public String toString() {
        return "CartItem{" + "userId=" + userId + ", articleId=" + articleId + ", size='" + size + '\'' + ", quantity=" + quantity + '}';
    }
    public Article getArticle() {
        return this.article;
    }
}