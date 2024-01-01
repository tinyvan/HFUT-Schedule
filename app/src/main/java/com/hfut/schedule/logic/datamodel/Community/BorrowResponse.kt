package com.hfut.schedule.logic.datamodel.Community

data class BorrowResponse(val result : BorrowResult)

data class BorrowResult(val records : List<BorrowRecords>)

data class BorrowRecords(val bookName : String,
                         val author : String,
                         val outTime : String,
                         val returnTime : String?,
                         val callNumber : String)
