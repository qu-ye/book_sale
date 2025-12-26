package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.Book;
import org.json.JSONObject;
import org.json.JSONArray;

public class DoubanUtil {

    private static final String SEARCH_URL_TEMPLATE = "https://search.douban.com/book/subject_search?search_text=";

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36";

    private Random random = new Random();
    
   
    private String getRandomUserAgent() {
        return USER_AGENT;
    }
    
    private Document getDocument(String url) throws Exception {
        System.out.println("正在访问URL: " + url);
        
        // 添加随机延时，模拟人类操作，避免被反爬虫
        Thread.sleep(2000 + random.nextInt(3000));
        
        // 创建并设置请求所需的Cookie
        Map<String, String> cookies = new HashMap<>();
        cookies.put("bid", generateBid()); // 生成随机的bid
        cookies.put("ll", "108288");
        cookies.put("ap_v", "0,6.0");
        
        // 使用Jsoup构建并执行HTTP请求，设置各种请求头和参数
        org.jsoup.Connection.Response response = Jsoup.connect(url)
            .userAgent(getRandomUserAgent())
            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
            .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
            .header("Accept-Encoding", "gzip, deflate")
            .header("Connection", "keep-alive")
            .header("Host", url.contains("m.douban.com") ? "m.douban.com" : "book.douban.com")
            .header("Sec-Fetch-Dest", "document")
            .header("Sec-Fetch-Mode", "navigate")
            .header("Sec-Fetch-Site", "none")
            .header("Sec-Fetch-User", "?1")
            .header("Upgrade-Insecure-Requests", "1")
            .header("Cache-Control", "max-age=0")
            .cookies(cookies)
            .timeout(15000) // 设置超时时间
            .maxBodySize(0) // 不限制响应体大小
            .followRedirects(true) // 允许重定向
            .execute(); // 执行请求

        // 检查响应的内容类型，判断是否被反爬虫拦截
        String contentType = response.contentType();
        if (contentType == null || !contentType.contains("text/html")) {
            System.out.println("警告：返回内容类型不是HTML，可能被豆瓣反爬虫拦截，Content-Type: " + contentType);
            throw new Exception("豆瓣反爬虫，返回内容类型不是HTML，无法解析！");
        }

        // 解析HTML响应为Document对象
        Document doc = response.parse();
            
        System.out.println("成功获取页面内容");
        
        // 打印页面内容预览以便调试
        System.out.println("页面内容预览: " + doc.text().substring(0, Math.min(500, doc.text().length())));
        
        return doc;
    }
    
   
    private String generateBid() {
        // 用于构建随机bid字符串的StringBuilder
        StringBuilder bid = new StringBuilder();
        // 包含所有可能的字符集（数字、大小写字母）
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // 循环11次生成11位随机字符
        for (int i = 0; i < 11; i++) {
            // 从字符集中随机选择一个字符并追加到bid
            bid.append(chars.charAt(random.nextInt(chars.length())));
        }
        // 返回生成的bid字符串
        return bid.toString();
    }

    public Book fetchBookData(String bookTitle) throws Exception {

        String bookPageUrl = searchForBookPage(bookTitle);
        if (bookPageUrl == null) {
            return null; // 没有找到书籍
        }


        Document bookPage = Jsoup.connect(bookPageUrl).userAgent(USER_AGENT).get();
                                
                                // 对象: Book (类), book (Book对象)
                                Book book = new Book();

        String title = bookPage.selectFirst("h1 span").text();
        book.setBook_name(title);
        

        Element coverElement = bookPage.selectFirst("#mainpic img");
        if (coverElement != null) {
            String coverUrl = coverElement.attr("src");
            String localCoverPath = downloadImage(coverUrl, title);
            book.setBook_cover(localCoverPath);
        }


        Element infoElement = bookPage.selectFirst("#info");
        if(infoElement != null) {
            Map<String, String> infoMap = parseInfo(infoElement.html());
            
            book.setBook_writer(infoMap.getOrDefault("作者", ""));
            book.setBook_publish(infoMap.getOrDefault("出版社", ""));
            book.setBook_isbn(infoMap.getOrDefault("ISBN", ""));
            
            String priceStr = infoMap.getOrDefault("定价", "0.0").replaceAll("[^0-9.]", "");
            if(!priceStr.isEmpty()){
                book.setBook_price(Double.parseDouble(priceStr));
            } else {
                book.setBook_price(0.0);
            }
        }
        
 
        Element descriptionParent = bookPage.selectFirst(".related_info");
        if (descriptionParent != null) {
            StringBuilder description = new StringBuilder();
            
            // 获取内容简介
            Elements introElements = descriptionParent.select(".intro");
            for (Element intro : introElements) {
                String introText = intro.select("p").text().trim();
                if (!introText.isEmpty()) {
                    description.append("内容简介：\n").append(introText).append("\n\n");
                }
            }
            
            // 获取作者简介
            Elements authorIntroElements = descriptionParent.select("#content .indent .intro");
            for (Element authorIntro : authorIntroElements) {
                String authorIntroText = authorIntro.select("p").text().trim();
                if (!authorIntroText.isEmpty() && !description.toString().contains(authorIntroText)) {
                    description.append("作者简介：\n").append(authorIntroText);
                }
            }
            
            book.setBook_description(description.toString().trim());
        }

        // 返回填充好信息的Book对象
        return book;
    }

 
    private String searchForBookPage(String bookTitle) throws Exception {

        String searchUrl = SEARCH_URL_TEMPLATE + URLEncoder.encode(bookTitle, StandardCharsets.UTF_8.toString());
        Document doc = Jsoup.connect(searchUrl).userAgent(USER_AGENT).get();    
        Elements searchResults = doc.select(".subject-item .info a");

        if (searchResults.isEmpty()) {
            searchResults = doc.select(".item-root .title a");
        }

        // 如果前两个选择器都未找到结果，则尝试使用更旧的列表式布局选择器
        if (searchResults.isEmpty()) {
            searchResults = doc.select("div.result-list div.result h3 a");
        }

        // 如果所有选择器都未找到结果，则返回null
        if (searchResults.isEmpty()) {
            return null;
        }

        // 返回第一个搜索结果的href属性（即图书详情页URL）
        return searchResults.first().attr("href");
    }
    
  
    private Map<String, String> parseInfo(String html) {
        // 创建一个HashMap来存储解析出的信息
        Map<String, String> infoMap = new HashMap<>();
        // 使用<br>标签分割HTML字符串，得到每一行信息
        String[] lines = html.split("<br>");
        // 遍历每一行信息
        for (String line : lines) {
            // 将当前行解析为Jsoup Document对象，以便提取文本
            Document lineDoc = Jsoup.parseBodyFragment(line);
            // 获取当前行的纯文本内容
            String lineText = lineDoc.text();
            
            // 使用冒号:分割键和值，限制只分割一次
            String[] parts = lineText.split(":", 2);
            // 如果分割后有键和值两部分
            if(parts.length > 1) {
                // 提取键并去除首尾空格
                String key = parts[0].trim();
                // 提取值并去除首尾空格
                String value = parts[1].trim();
                // 将键值对存储到Map中
                infoMap.put(key, value);
            }
        }
        // 返回包含所有解析信息的Map
        return infoMap;
    }

    private String downloadImage(String imageUrl, String bookName) {
        try {
            // 创建URL对象
            URL url = new URL(imageUrl);
            // 从URL路径中提取文件名
            String fileName = new File(url.getPath()).getName();
            // 对书名进行安全处理，替换文件名中不允许的字符为下划线
            String safeBookName = bookName.replaceAll("[\\/:*?\"<>|]", "_");
            // 组合生成本地文件名
            String localFileName = safeBookName + "_" + fileName;
            // 创建封面图片存放目录对象
            File coverDir = new File("img/book_cover");
            // 如果目录不存在，则创建目录
            if (!coverDir.exists()) {
                coverDir.mkdirs();
            }
            // 创建本地输出文件对象
            File outputFile = new File(coverDir, localFileName);
            // 用URLConnection并设置请求头，模拟浏览器访问，避免反爬虫
            java.net.URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Referer", "https://book.douban.com/"); // 设置Referer，模拟从豆瓣页面跳转
            // 使用try-with-resources确保输入流和输出流自动关闭
            try (InputStream in = conn.getInputStream(); // 获取输入流
                 FileOutputStream out = new FileOutputStream(outputFile)) { // 获取输出流
                byte[] buffer = new byte[4096]; // 创建缓冲区
                int n; // 记录读取的字节数
                // 循环读取数据并写入文件
                while ((n = in.read(buffer)) != -1) {
                    out.write(buffer, 0, n);
                }
            }
            // 返回本地图片文件的相对路径
            return "img/book_cover/" + localFileName;
        } catch (Exception e) {
            // 捕获并处理下载图片时发生的异常
            System.err.println("下载图片失败: " + imageUrl);
            e.printStackTrace(); // 打印异常堆栈信息
            return null; // 下载失败返回null
        }
    }

    
    // 新增：基于JSON的批量搜索豆瓣图书
    public List<Book> searchBooksByJson(String bookTitle) throws Exception {
        // 创建一个ArrayList用于存储搜索到的Book对象
        List<Book> books = new ArrayList<>();
        // 构建搜索URL，并对图书标题进行URL编码
        String searchUrl = SEARCH_URL_TEMPLATE + URLEncoder.encode(bookTitle, StandardCharsets.UTF_8.toString());
        // 访问搜索URL并获取HTML文档
        Document doc = Jsoup.connect(searchUrl).userAgent(USER_AGENT).get();
        // 获取页面的完整HTML内容
        String html = doc.html();
        // 提取window.__DATA__ = {...}; 中的JSON数据
        // 定义正则表达式，用于匹配JavaScript变量window.__DATA__的值
        Pattern p = Pattern.compile("window\\.__DATA__\\s*=\\s*(\\{.*?\\});");
        Matcher m = p.matcher(html); // 创建匹配器
        // 如果找到匹配项
        if (m.find()) {
            String jsonStr = m.group(1); // 提取JSON字符串
            JSONObject obj = new JSONObject(jsonStr); // 将JSON字符串解析为JSONObject
            JSONArray items = obj.getJSONArray("items"); // 获取items数组，其中包含图书信息
            // 遍历items数组中的每个图书对象
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i); // 获取当前图书的JSON对象
                Book book = new Book(); // 创建一个新的Book对象
                book.setBook_name(item.optString("title")); // 设置书名
                // 下载封面图片到本地
                String coverUrl = item.optString("cover_url"); // 获取封面URL
                if (coverUrl != null && !coverUrl.isEmpty()) {
                    String localCoverPath = downloadImage(coverUrl, book.getBook_name()); // 下载图片
                    book.setBook_cover(localCoverPath); // 设置本地封面路径
                }
                // 获取详情页URL并访问详情页获取ISBN、简介、价格等
                String detailUrl = item.optString("url"); // 获取详情页URL
                Element infoElement = null; // 用于存储详情页信息元素
                if (detailUrl != null && !detailUrl.isEmpty()) {
                    try {
                        // 访问图书详情页并获取HTML文档
                        Document detailDoc = Jsoup.connect(detailUrl).userAgent(USER_AGENT).get();
                        // 选择详情页中的信息区域
                        infoElement = detailDoc.selectFirst("#info");
                        if (infoElement != null) {
                            String infoText = infoElement.html(); // 获取信息区域的HTML内容
                            // 使用正则表达式匹配ISBN
                            Pattern isbnPattern = Pattern.compile("ISBN[^\\d]*(\\d[\\d-]*)");
                            Matcher isbnMatcher = isbnPattern.matcher(infoText); // 创建ISBN匹配器
                            if (isbnMatcher.find()) {
                                String isbn = isbnMatcher.group(1).replaceAll("-", ""); // 提取并清理ISBN
                                book.setBook_isbn(isbn); // 设置ISBN
                            }
                            // 获取完整的图书简介
                            Element descriptionParent = detailDoc.selectFirst(".related_info");
                            if (descriptionParent != null) {
                                StringBuilder description = new StringBuilder();
                                Elements introElements = descriptionParent.select(".intro"); // 获取内容简介元素
                                for (Element intro : introElements) {
                                    String introText = intro.select("p").text().trim();
                                    if (!introText.isEmpty()) {
                                        description.append(introText).append("\n\n");
                                    }
                                }
                                if (description.length() > 0) {
                                    book.setBook_description(description.toString().trim()); // 设置简介
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("获取详情页信息失败: " + detailUrl); // 打印错误信息
                    }
                }
                // 作者/出版社/价格等在abstract字段里
                String abs = item.optString("abstract"); // 获取abstract字段内容
                String[] parts = abs.split("/"); // 按斜杠分割
                if (parts.length >= 1) book.setBook_writer(parts[0].trim()); // 设置作者
                if (parts.length >= 2) book.setBook_publish(parts[1].trim()); // 设置出版社
                // 优先从abstract字段提取价格
                boolean hasPrice = false; // 标志是否已提取价格
                for (String part : parts) {
                    if (part.contains("元")) {
                        String priceStr = part.replaceAll("[^0-9.]", "");
                        if (!priceStr.isEmpty()) {
                            book.setBook_price(Double.parseDouble(priceStr));
                            hasPrice = true;
                        }
                    }
                }
                // 如果没有价格，再从详情页info里找
                if (!hasPrice && infoElement != null) {
                    String infoText = infoElement.html();
                    Pattern pricePattern = Pattern.compile("定价[:：]?\\s*([0-9.]+)");
                    Matcher priceMatcher = pricePattern.matcher(infoText);
                    if (priceMatcher.find()) {
                        book.setBook_price(Double.parseDouble(priceMatcher.group(1)));
                    }
                }
                // 如果没有获取到简介，则使用评分信息
                if (book.getBook_description() == null || book.getBook_description().isEmpty()) {
                    if (item.has("rating")) {
                        JSONObject rating = item.getJSONObject("rating");
                        book.setBook_description("评分:" + rating.optDouble("value", 0));
                    } else {
                        book.setBook_description("评分: 暂无");
                    }
                }
                books.add(book); // 将填充好信息的Book对象添加到列表中
            }
        }
        return books; // 返回图书列表
    }
} 