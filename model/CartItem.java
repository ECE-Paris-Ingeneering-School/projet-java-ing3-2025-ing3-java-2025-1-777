package model;

public class CartItem {
    private int userId;
    private int articleId;
    private int quantity;

    public CartItem() {}
    public CartItem(int userId, int articleId, int quantity) {
        this.userId = userId;
        this.articleId = articleId;
        this.quantity = quantity;
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

    public int getQuantity() { 
      return quantity;
    }
    public void setQuantity(int quantity) {
      this.quantity = quantity; 
    }
}
