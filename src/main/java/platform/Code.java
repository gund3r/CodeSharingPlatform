package platform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "CODE")
@Component
public class Code {

    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID uuid;

    @NotBlank
    private String code = "";

    private String date;

    private int time;

    private int views;

    private boolean isTimeLimit;

    private boolean isViewsLimit;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime localDateTime;

    public Code () {}

    public Code(String code) {
        this.code = code;
        this.date = FormatDataTime.getFormatterDateTime();
        this.localDateTime = LocalDateTime.now();
        this.time = 0;
        this.views = 0;
        this.isTimeLimit = false;
        this.isViewsLimit = false;
    }

    public Code(String code, Integer time, Integer views) {
        this.code = code;
        this.date = FormatDataTime.getFormatterDateTime();
        this.localDateTime = LocalDateTime.now();
        this.time = time;
        this.views = views;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime() {
        this.localDateTime = LocalDateTime.now();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    @JsonIgnore
    public boolean isTimeLimit() {
        return isTimeLimit;
    }

    public void setTimeLimit(boolean timeLimit) {
        isTimeLimit = timeLimit;
    }
    @JsonIgnore
    public boolean isViewsLimit() {
        return isViewsLimit;
    }

    public void setViewsLimit(boolean viewsLimit) {
        isViewsLimit = viewsLimit;
    }

    @Override
    public String toString() {
        return "platform.Code{" +
                "uuid=" + uuid +
                ", code='" + code + '\'' +
                ", date='" + date + '\'' +
                ", time=" + time +
                ", views=" + views +
                '}';
    }
}
