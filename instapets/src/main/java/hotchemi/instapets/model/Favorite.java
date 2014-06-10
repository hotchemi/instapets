package hotchemi.instapets.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

import hotchemi.instapets.entity.PhotoEntity;

@Table(name = "Favorites")
public final class Favorite extends Model {

    @Column(name = "image_url")
    public String imageUrl;

    @Column(name = "link")
    public String link;

    public Favorite() {
        super();
    }

    public Favorite(String imageUrl, String link) {
        this();
        this.imageUrl = imageUrl;
        this.link = link;
    }

    public Favorite(PhotoEntity entity) {
        this(entity.imageUrl, entity.link);
    }

    public static List<Favorite> getAllItem() {
        return new Select().from(Favorite.class).execute();
    }

    public static void deleteFromItem(Favorite item) {
        new Delete().from(Favorite.class).where("Id = ?", item.getId()).execute();
    }

}