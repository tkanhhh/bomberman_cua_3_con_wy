<h1 align="center">------------- BOMBERMAN_CỦA_3_CON_WỶ -------------</h1>
<br />
<h1 align="center">Table of contents 📖</h1>

<h2 align="center">
  I. Giới thiệu chung
  <br />
  II. About Game
  <br />
  III. UML Diagram
  <br />
  IV. Thuật toán và kĩ thuật lập trình đã sử dụng
  <br />
  V. Kết luận, hướng phát triển, điều rút ra được
  <br />
</h2>
<br />

## I. Giới thiệu chung
1. Tên nhóm: OOP_N2_BTL_N2
2. Lớp: INT2204 20 - Lập trình hướng đối tượng
3. Tên game: Bomberman của 3 con wỷ
4. Thành viên nhóm:

| Order |        Name        |    ID    |        Email        |                Github account                 |
| :---: |:------------------:|:--------:|:-------------------:|:---------------------------------------------:|
|   1   | Nguyễn Phương Linh | 21020545 | 21020545@vnu.edu.vn |      [Elyy27](https://github.com/Elyy27)      |
|   2   |   Trịnh Kiều Anh   | 21020282 | 21020282@vnu.edu.vn |     [tkanhhh](https://github.com/tkanhhh)     |
|   3   |    Hồ Thu Giang    | 21020309 | 21020309@vnu.edu.vn | [hothugiang](https://github.com/hothugiang)   |


## II. About Game 🎮
### 1. Thông tin chung
- Ngôn ngữ: [JAVA](https://www.java.com/en/)
- Phiên bản SDK: Java 18
- IDE: [IntelliJ](https://www.jetbrains.com/idea/)
- Thư viện đồ hoạ: [JavaFx](https://openjfx.io)

### 2. Cách chơi

#### [Video demo](https://youtu.be/0EiIEkmMF4w)

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
<Ai đọ vít hộ chứ cí nì t hum bíc vt =))))))>

## III. UML Diagram
![Untitled Workspace](https://user-images.githubusercontent.com/100185884/197350219-35673018-6ed7-4f77-b935-281a9822938c.png)

## IV. Thuật toán và kĩ thuật lập trình đã sử dụng
- Thuật toán tìm đường của Boss: Dijkstra

- Random map

- Random tốc độ của quái

- Sử dụng Javafx để chèn ảnh, chèn nhạc, chèn hiệu ứng trong game

## V. Kết luận, hướng phát triển, điều rút ra được
### 1. Kết luận, điều rút ra được
- Comment sau mỗi đoạn code

- Học được thêm thư viện Javafx, cách quản lý file, đặt tên biến để dễ quản lý, kĩ năng tra google, tự học,...

- Học thêm một số hàm, thư viện chưa gặp trong các bài tập

### 2. Hướng phát triển, cải tiến

