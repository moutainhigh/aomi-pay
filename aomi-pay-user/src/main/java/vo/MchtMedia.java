package vo;

import lombok.Data;

import java.util.List;

/**
 * @Author 李赛赛
 * @Date 2020/8/1 17:28
 * @Version 1.0
 */
@Data
public class MchtMedia {

    private String certFront;
    private List<String> industryLicenses;
    private String bankCardPositive;
    private String handheld;
    private List<String> extraPics;
    private String certReverse;

}
