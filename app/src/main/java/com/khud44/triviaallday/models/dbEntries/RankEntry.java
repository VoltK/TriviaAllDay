package com.khud44.triviaallday.models.dbEntries;

import android.arch.persistence.room.*;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "ranks", foreignKeys = @ForeignKey(entity = CategoryEntry.class,
                                                        parentColumns = "id",
                                                        childColumns = "categoryId",
                                                        onDelete = CASCADE))
public class RankEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int score;
    private String level;
    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    private int categoryId;

    @Ignore
    public RankEntry(int score, String level, int categoryId, Date updatedAt) {
        //this.category = category;
        this.score = score;
        this.level = level;
        this.categoryId = categoryId;
        this.updatedAt = updatedAt;
    }

    public RankEntry(int id, int categoryId, int score, String level, Date updatedAt) {
        this.id = id;
        this.score = score;
        this.level = level;
        this.categoryId = categoryId;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int category) {
        this.categoryId = category;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
