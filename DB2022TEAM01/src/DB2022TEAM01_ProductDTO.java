
public class DB2022TEAM01_ProductDTO {
	private Long userId;
    private String name;
    private Long price;
    private String seller;
    private String category;
    private String IdolGroup;
    private String IdolMember;

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getSeller() {
        return seller;
    }

    public String getCategory() {
        return category;
    }

    public String getIdolGroup() {
        return IdolGroup;
    }

    public String getIdolMember() {
        return IdolMember;
    }

    public DB2022TEAM01_ProductDTO(Long userId, String name, Long price, String seller, String category, String idolGroup, String idolMember) {
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.seller = seller;
        this.category = category;
        IdolGroup = idolGroup;
        IdolMember = idolMember;
    }
}
