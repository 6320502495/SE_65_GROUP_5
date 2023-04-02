<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
    <head>
        <title>หน้าเอกสาร (รับคำร้อง)</title>
    </head>
    <body>
        <h1>หน้าสำหรับผู้ตรวจสอบการร้องเรียน</h1>
        <?php
            use App\Http\Controllers\DocumentInspectionController;
            $reportUsers = DocumentInspectionController::showAll();
            echo "<table border=1>";
                echo "<tr><th>ชื่อบัญชี</th><th>ชื่อ</th><th>นามสกุล</th><th>วันเกิด</th><th>สาเหตุที่ถูกร้องเรียน</th><th colspan=2>ตัวเลือก</th></tr>";
                foreach($reportUsers as $reportUser) {
                    echo "<tr>";
                    echo "<td>", $reportUser->Account_Name, "</td>";
                        echo "<td>", $reportUser->Account_Firstname, "</td>";
                        echo "<td>", $reportUser->Account_Surname, "</td>";
                        echo "<td>", $reportUser->Account_Birthday, "</td>";
                        echo "<td>", $reportUser->Report_Reason, "</td>";
                        echo "<td>";
                            echo "ปฏิเสธการร้องเรียน";
                        echo "</td>";
                        echo "<td>";
                            echo "ส่งต่อการร้องเรียน";
                        echo "</td>";
                    echo "</tr>";
                };
            echo "</table>";
        ?>
    </body> 
</html>
