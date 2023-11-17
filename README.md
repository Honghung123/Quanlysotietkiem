### Nếu chưa biết làm gì thì đọc hướng dẫn  https://www.notion.so/honghung123/MongoDB-041f51a666cc4aafb7a2f5b94e036419?pvs=4

## Trước khi clone project, Nếu Ai muốn thao tác database riêng thì thực hiện dưới đây, không thì gửi email để t add vào
- Trong thư mục Resources/static, ae copy index.html chạy trên Node js hoặc 
chạy bằng localhost:8080 cx đc hoặc tạo copy một file html rồi mở file html bằng liveserver của vscode cx được luôn

# Tạo database riêng
- Tạo collection "tbl_kyhan" rồi nhấn INSERT DOCUMENT rồi lần lượt thêm 3 
  cái này

{"_id":{"$oid":"654d9fbaf1aded54971329da"},"type":{"$numberInt":"0"},"name":"Không kỳ hạn","laisuat":{"$numberDouble":"0.0015"},"dcguithem":{"$numberLong":"100000"},"ngaydcrut":{"$numberInt":"15"},"min_deposit":{"$numberLong":"100000"},"month":{"$numberInt":"0"}}

{"_id":{"$oid":"655451ecdbe7bc0182ccb200"},"type":{"$numberInt":"1"},"name":"6 tháng","laisuat":{"$numberDouble":"0.0055"},"dcguithem":{"$numberLong":"0"},"ngaydcrut":{"$numberInt":"15"},"min_deposit":{"$numberLong":"100000"},"month":{"$numberInt":"6"}}

{"_id":{"$oid":"6554520adbe7bc0182ccb201"},"type":{"$numberInt":"2"},"name":"3 tháng","laisuat":{"$numberDouble":"0.005"},"dcguithem":{"$numberLong":"0"},"ngaydcrut":{"$numberInt":"15"},"min_deposit":{"$numberLong":"100000"},"month":{"$numberInt":"3"}}



- Tạo collection "tbl_customer" rồi nhấn INSERT DOCUMENT rồi thêm cái này

{"_id":{"$oid":"655724a4ecb4265e841ae01c"},"makh":{"$numberInt":"1"},"name":"Trần Phan Phúc Ân","address":"Quận Tân Bình, Hồ Chí Minh City","cmnd":"0121212233","sotk":{"maso":{"$numberInt":"1"},"status":{"$numberInt":"1"},"type":{"$numberInt":"0"},"dateCreated":{"$date":{"$numberLong":"1698858000000"}},"money":"600000","kyHan":{"_id":{"$oid":"654d9fbaf1aded54971329da"},"type":{"$numberInt":"0"},"name":"Không kỳ hạn","month":{"$numberInt":"0"},"laisuat":{"$numberDouble":"0.0015"},"dcguithem":"100000","ngaydcrut":{"$numberInt":"15"},"min_deposit":"100000"}},"_class":"com.earntogether.qlysotietkiem.entity.Customer"}



- Tạo collection "tbl_sotietkiem" rồi nhấn INSERT DOCUMENT rồi thêm cái này

{"_id":{"$oid":"655724a4ecb4265e841ae01d"},"maso":{"$numberInt":"1"},"status":{"$numberInt":"1"},"type":{"$numberInt":"0"},"dateCreated":{"$date":{"$numberLong":"1698858000000"}},"money":"600000","kyHan":{"_id":{"$oid":"654d9fbaf1aded54971329da"},"type":{"$numberInt":"0"},"name":"Không kỳ hạn","month":{"$numberInt":"0"},"laisuat":{"$numberDouble":"0.0015"},"dcguithem":"100000","ngaydcrut":{"$numberInt":"15"},"min_deposit":"100000"},"_class":"com.earntogether.qlysotietkiem.entity.SoTietKiem"}



- Tạo collection "tbl_phieugoitien"

{"_id":{"$oid":"6555961d966b486c1a667eb6"},"makh":{"$numberInt":"1"},"maso":{"$numberInt":"1"},"type":{"$numberInt":"1"},"date":{"$date":{"$numberLong":"1699635600000"}},"money":"400000","_class":"com.earntogether.starter_project.entity.PhieuGoiTien"}



- Tạo collection "tbl_phieuruttien"

{"_id":{"$oid":"6555939c966b486c1a667eaa"},"makh":{"$numberInt":"1"},"maso":{"$numberInt":"1"},"type":{"$numberInt":"1"},"date":{"$date":{"$numberLong":"1699635600000"}},"money":"100000","_class":"com.earntogether.starter_project.entity.PhieuRutTien"}










