//public static ArrayList<Item> getAllItems() {
//        ArrayList<Item> allItemsList = new ArrayList<>();
//        Map<Integer, List<Category>> itemCategoriesMap = new HashMap<>();
//
//        String sql= "SELECT i.*, b.*, c.*, s.*, cat.*, hc.* " +
//        "FROM item i " +
//        "JOIN brand b ON i.brand_id = b.id " +
//        "JOIN color c ON i.color_id = c.id " +
//        "JOIN size s ON i.size_id = s.id " +
//        "JOIN has_category hc ON i.id = hc.item_id " +
//        "JOIN category cat ON hc.category_id = cat.id";
//
//        try (Connection con = DriverManager.getConnection(
//        p.getProperty("connectionString"),
//        p.getProperty("name"),
//        p.getProperty("password"))) {
//        Statement st = con.createStatement();
//        ResultSet rs = st.executeQuery(sql);
//
//        // lista över alla items i databasen
//        while (rs.next()) {
//        Item item = new Item(
//        rs.getInt("i.id"),
//        new Brand(rs.getInt("b.id"), rs.getString("b.name")),
//        rs.getString("model"),
//        new Size(rs.getInt("s.id"), rs.getString("s.size")),
//        rs.getDouble("price"),
//        new Color(rs.getInt("c.id"), rs.getString("c.name")),
//        rs.getInt("stock_balance"));
//        allItemsList.add(item);
//
//        // mappar rätt kategorier till rätt item genom has_category tabellen
//        if (itemCategoriesMap.containsKey(rs.getInt("i.id"))) {
//        itemCategoriesMap.get(rs.getInt("i.id")).add(
//        new Category(rs.getInt("cat.id"), rs.getString("cat.name")));
//        } else {
//        List<Category> categories = new ArrayList<>();
//        categories.add(
//        new Category(rs.getInt("cat.id"), rs.getString("cat.name")));
//        itemCategoriesMap.put(rs.getInt("i.id"), categories);
//        }
//
//        // lägger till rätt kategorier i item
//        item.setCategories(itemCategoriesMap.get(rs.getInt("i.id")));
//        }
//        return allItemsList;
//        } catch (SQLException ex) {
//        ex.printStackTrace();
//        System.out.println("SQL exception");
//        return null;
//        }
//        }

//private List<Category> categories;

//public List<Category> getCategories() {
//        return categories;
//        }
//
//public void setCategories(List<Category> categories) {
//        this.categories = categories;
//        }
//public String minimumToString() {
//        return brand_id.getName() + " " +
//        model + ", " +
//        color_id.getName() + ", " +
//        size_id.getSize() + ", Price: " +
//        price + " SEK";
//        }