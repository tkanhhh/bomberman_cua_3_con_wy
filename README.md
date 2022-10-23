<h1 align="center">------------- BOMBERMAN_CỦA_3_CON_WỶ -------------</h1>

<h1 align="center">Table of contents 📖</h1>

<h2 align="center">
  <a href="#introduction">I. Giới thiệu chung</a>
  <br />
  <a href="#about">II. About Game</a>
  <br />
  <a href="#uml">III. UML Diagram</a>
  <br />
  <a href="#algorithm">IV. Thuật toán và kĩ thuật lập trình đã sử dụng</a>
  <br />
  <a href="#conclusion">V. Kết luận, hướng phát triển, điều rút ra được</a>
  <br />
</h2>
<br />

## I. Giới thiệu chung <a name="introduction"></a>
1. Tên nhóm: OOP_N2_BTL_N2
2. Lớp: INT2204 20 - Lập trình hướng đối tượng
3. Tên game: Bomberman của 3 con wỷ
4. 3 con wỷ:  :alien:

| Order |        Name        |    ID    |        Email        |                Github account                 |
| :---: |:------------------:|:--------:|:-------------------:|:---------------------------------------------:|
|   1   | Nguyễn Phương Linh | 21020545 | 21020545@vnu.edu.vn |      [Elyy27](https://github.com/Elyy27)      |
|   2   |   Trịnh Kiều Anh   | 21020282 | 21020282@vnu.edu.vn |     [tkanhhh](https://github.com/tkanhhh)     |
|   3   |    Hồ Thu Giang    | 21020309 | 21020309@vnu.edu.vn | [hothugiang](https://github.com/hothugiang)   |


## II. About Game 🎮 <a name="about"></a>
### 1. Thông tin chung
- Ngôn ngữ: [JAVA](https://www.java.com/en/)
- Phiên bản SDK: [SDK 18](https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html)
- IDE: [IntelliJ](https://www.jetbrains.com/idea/)
- Thư viện đồ hoạ: [JavaFx](https://openjfx.io)

### 2. Cách chơi

#### [VIDEO DEMO](https://youtu.be/0EiIEkmMF4w)


Di chuyển Bomberman lên xuống, thả bom để giết quái phá gạch, tìm cổng ![](res/sprites/portal.png) để qua màn

>**Enemies** 
>
![](res/sprites/dsa.png) *DSA*: Di chuyển tự do với tốc độ không đổi

![](res/sprites/oop.png) *OOP*: Di chuyển tự do, có thể thay đổi tốc độ

![](res/sprites/ca.png) *ComputerArchitecture*: Di chuyển tự do, có thể thay đổi tốc độ

![](res/sprites/boss.png) *UET_BOSS*: Di chuyển tự do, có thể thay đổi tốc độ, tìm đường đuổi theo Bomberman, có thể hồi sinh số lần nhất định sau khi trúng bom


>**PowerUp** 
>
![](res/sprites/powerup_speed.png) *Speed*: Tăng tốc độ Bomberman

![](res/sprites/powerup_flames.png) *Flames*: Tăng độ sát thương của bom nổ

![](res/sprites/powerup_bombs.png) *Bombs*: Tăng số bom có thể đặt trên map

>**Map** 
>
![](res/sprites/brick.png) *Gạch*: Có thể đặt bom để phá

![](res/sprites/wall.png) *Tường*: Chặn mọi chuyển động, không thể đi xuyên qua

### 3. Các tính năng
- Chế độ 1 người chơi, chế độ 2 người chơi
- Nhân vật chết nếu chạm bom hoặc quái
- Quái tìm đường để đuổi theo enemy, quái thay đổi tốc độ
- Hồi sinh boss
- Chuyển level, tạo map mới
- Random tạo hình Bomberman ở mỗi level
- Random map, powerup và portal
- Phát nhạc nền, sound effect

### 4. Cách cài đặt
a. Cài đặt IDE, thư viện đồ hoạ
- Cái Intellij, Java 18

- Cài javafx: [JavaFx](https://openjfx.io)

b. Tải code
- Lên đầu trang: https://github.com/tkanhhh/bomberman_cua_3_con_wy

- Bấm vào nút code màu xanh lá bên góc phải => Download ZIP

- Giải nén

c. Cài đặt và chạy game
- Mở Intellij, mở project vừa tải

- Chọn Run/Edit Configurations 

- Ấn dấu + bên góc trái, chọn Application

- Modify options/Add VM Options

- Tại ô VM Options nhập --module-path <Đường dẫn tới folder lib của javafx> --add-modules=ALL-MODULE-PATH
*VD:* --module-path C:/javafx-sdk-19/lib/ --add-modules=ALL-MODULE-PATH

- Tại ô main điền BombermanGame

- Apply/Ok => Chạy thử game

## III. UML Diagram <a name="uml"></a>
![BST (3)](https://user-images.githubusercontent.com/100121386/197376395-1a2da0a3-2a06-4943-b595-e01ac7bc1218.png)

## IV. Thuật toán và kĩ thuật lập trình đã sử dụng <a name="algorithm"></a>
- Thuật toán tìm đường của Boss: Dijkstra

- Random map

- Random tốc độ của quái

- Sử dụng Javafx để chèn ảnh, chèn nhạc, chèn hiệu ứng trong game

## V. Kết luận, hướng phát triển, điều rút ra được <a name="conclusion"></a>
### 1. Kết luận, điều rút ra được
- Comment sau mỗi đoạn code

- Học được thêm thư viện Javafx, cách quản lý file, đặt tên biến để dễ quản lý, kĩ năng tra google, tự học,...

- Học thêm một số hàm, thư viện chưa gặp trong các bài tập

### 2. Hướng phát triển, cải tiến
- Thuật toán AI cho máy tự chơi
- Cải tiến thuật toán di chuyển, xử lý va chạm khi đặt bomb trùng với vị trí của quái
- Tạo playlist nhạc BGM
- Cho phép chọn giao diện Bomberman
- ...
