package com.arkitvora.categorizedgalleryview;

public class ImagePosition {

    int categoryPosition;
    int positionInCategory;

    public ImagePosition(int categoryPosition , int positionInCategory) {
        this.categoryPosition = categoryPosition;
        this.positionInCategory = positionInCategory;
    }

    public int getCategoryPosition() {
        return categoryPosition;
    }

    public void setCategoryPosition(int categoryPosition) {
        this.categoryPosition = categoryPosition;
    }

    public int getPositionInCategory() {
        return positionInCategory;
    }

    public void setPositionInCategory(int positionInCategory) {
        this.positionInCategory = positionInCategory;
    }
}
