### Nếu chưa biết làm gì thì đọc hướng dẫn  https://www.notion.so/honghung123/MongoDB-041f51a666cc4aafb7a2f5b94e036419?pvs=4

- NOTE: AE run project rồi test api trong http://localhost:8080 . Nếu muốn 
  test trên Node js thì copy index.html trong thư mục Resources/static,hoặc tạo
một file html rồi mở file html bằng live server của vscode cx được luôn

## Trước khi clone project, Nếu Ai muốn thao tác database riêng thì thực hiện dưới đây, không thì gửi email đã đăng kí mongoDB để tui invite 
# 🎉 NEW UPDATE - Giờ đây không cần làm gì nhiều 
- Tạo collection ***tbl_customer_passbook***, ***tbl_depositSlip***, 
  ***tbl_withdrawalSlip*** và ***tbl_passbook*** (Không cần chứa dữ liệu) 
- Tạo collection ***tbl_kyhan*** rồi nhấn INSERT DOCUMENT rồi lần lượt thêm 3 
  cái này (Thực ra không thêm cũng được, nhưng muốn đăng ký sổ tiết kiệm thì 
  bắt buộc phải có kỳ hạn)

{"_id":{"$oid":"654d9fbaf1aded54971329da"},"type":{"$numberInt":"0"},"name":"Không kỳ hạn","dcguithem":{"$numberLong":"100000"},"ngaydcrut":{"$numberInt":"15"},"minDeposit":{"$numberLong":"100000"},"numOfMonths":{"$numberInt":"0"},"interestRate":{"$numberDouble":"0.0015"}}

{"_id":{"$oid":"655451ecdbe7bc0182ccb200"},"type":{"$numberInt":"1"},"name":"6 tháng","dcguithem":{"$numberLong":"0"},"ngaydcrut":{"$numberInt":"15"},"interestRate":{"$numberDouble":"0.0055"},"minDeposit":{"$numberLong":"100000"},"numOfMonths":{"$numberInt":"6"}}

{"_id":{"$oid":"6554520adbe7bc0182ccb201"},"type":{"$numberInt":"2"},"name":"3 tháng","dcguithem":{"$numberLong":"0"},"ngaydcrut":{"$numberInt":"15"},"interestRate":{"$numberDouble":"0.005"},"minDeposit":{"$numberLong":"100000"},"numOfMonths":{"$numberInt":"3"}}


* Note: Tạo xong thì nhớ copy ConnectionString của bạn đã copy lúc tạo rồi 
  paste vào .env nhe 








