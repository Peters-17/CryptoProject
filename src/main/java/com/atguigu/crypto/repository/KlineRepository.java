//package com.atguigu.crypto.repository;
//
//import com.atguigu.crypto.model.Kline;
//import org.apache.ibatis.annotations.*;
//
//import java.util.List;
//
//@Mapper
//public interface KlineRepository {
//
//
//    @Insert("INSERT INTO UIKlines (open_time, close_time, open_price, high_price, low_price, close_price, volume, quote_asset_volume, number_of_trades, taker_buy_base_asset_volume, taker_buy_quote_asset_volume, symbol_kline, interval_kline) " +
//            "VALUES (#{openTime}, #{closeTime}, #{openPrice}, #{highPrice}, #{lowPrice}, #{closePrice}, #{volume}, #{quoteAssetVolume}, #{numberOfTrades}, #{takerBuyBaseAssetVolume}, #{takerBuyQuoteAssetVolume}, #{symbolKline}, #{intervalKline}) " +
//            "ON DUPLICATE KEY UPDATE close_time = VALUES(close_time), open_price = VALUES(open_price), high_price = VALUES(high_price), low_price = VALUES(low_price), close_price = VALUES(close_price), volume = VALUES(volume), quote_asset_volume = VALUES(quote_asset_volume), number_of_trades = VALUES(number_of_trades), taker_buy_base_asset_volume = VALUES(taker_buy_base_asset_volume), taker_buy_quote_asset_volume = VALUES(taker_buy_quote_asset_volume)")
//    public int insertKline(Kline kline);
//
//    @Select("SELECT * FROM UIKlines WHERE symbol_kline = #{symbolKline} AND interval_kline = #{intervalKline} AND open_time = #{openTime}")
//    public List<Kline> findBySymbolIntervalAndOpenTime(String symbolKline, String intervalKline, Long openTime);
//
//    @Update("UPDATE UIKlines SET close_time = #{closeTime}, open_price = #{openPrice}, high_price = #{highPrice}, low_price = #{lowPrice}, close_price = #{closePrice}, volume = #{volume}, quote_asset_volume = #{quoteAssetVolume}, number_of_trades = #{numberOfTrades}, taker_buy_base_asset_volume = #{takerBuyBaseAssetVolume}, taker_buy_quote_asset_volume = #{takerBuyQuoteAssetVolume} " +
//            "WHERE symbol_kline = #{symbolKline} AND interval_kline = #{intervalKline} AND open_time = #{openTime}")
//    public int updateKline(Kline kline);
//
//    @Delete("DELETE FROM UIKlines WHERE symbol_kline = #{symbolKline} AND interval_kline = #{intervalKline} AND open_time = #{openTime}")
//    public void deleteBySymbolIntervalAndOpenTime(String symbolKline, String intervalKline, Long openTime);
//
//    @Select("SELECT * FROM UIKlines WHERE symbol_kline = #{symbolKline} AND interval_kline = #{intervalKline" +
//            "} ORDER BY open_time DESC LIMIT #{limit}")
//    public List<Kline> findRecentBySymbolAndInterval(String symbolKline, String intervalKline, int limit);
//
//    @Insert({
//            "<script>",
//            "insert into mybatis_demo (name, age)",
//            "values ",
//            "<foreach  collection='dmoList' item='dmo' separator=','>",
//            "( #{dmo.name}, #{dmo.age})",
//            "</foreach>",
//            "</script>"
//    })
//    int insertBatch(@Param("dmoList") List<Kline> dmoList);
//}
//
//
//
package com.atguigu.crypto.repository;

import com.atguigu.crypto.model.Kline;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KlineRepository {

    @Insert({
            "<script>",
            "INSERT INTO UIKlines (open_time, close_time, open_price, high_price, low_price, close_price, volume, quote_asset_volume, number_of_trades, taker_buy_base_asset_volume, taker_buy_quote_asset_volume, symbol_kline) ",
            "VALUES ",
            "<foreach collection='klineList' item='kline' separator=','>",
            "( #{kline.openTime}, #{kline.closeTime}, #{kline.openPrice}, #{kline.highPrice}, #{kline.lowPrice}, #{kline.closePrice}, #{kline.volume}, #{kline.quoteAssetVolume}, #{kline.numberOfTrades}, #{kline.takerBuyBaseAssetVolume}, #{kline.takerBuyQuoteAssetVolume}, #{kline.symbolKline} )",
            "</foreach>",
            "ON DUPLICATE KEY UPDATE close_time = VALUES(close_time), open_price = VALUES(open_price), high_price = VALUES(high_price), low_price = VALUES(low_price), close_price = VALUES(close_price), volume = VALUES(volume), quote_asset_volume = VALUES(quote_asset_volume), number_of_trades = VALUES(number_of_trades), taker_buy_base_asset_volume = VALUES(taker_buy_base_asset_volume), taker_buy_quote_asset_volume = VALUES(taker_buy_quote_asset_volume)",
            "</script>"
    })
    int insertBatch(@Param("klineList") List<Kline> klineList);

//    @Select("SELECT " +
//            "  FLOOR(open_time / (5 * 3600000)) * (5 * 3600000) AS open_time, " +
//            "  MAX(close_time) as close_time, " +
//            "  FIRST_VALUE(open_price) OVER (PARTITION BY FLOOR(open_time / (5 * 3600000)) ORDER BY open_time) as open_price, " +
//            "  MAX(high_price) as high_price, " +
//            "  MIN(low_price) as low_price, " +
//            "  LAST_VALUE(close_price) OVER (PARTITION BY FLOOR(open_time / (5 * 3600000)) ORDER BY open_time DESC) as close_price, " +
//            "  SUM(volume) as volume, " +
//            "  symbol_kline " +
//            "FROM UIKlines " +
//            "WHERE symbol_kline = #{symbolKline} AND open_time BETWEEN #{startTime} AND #{endTime} " +
//            "GROUP BY FLOOR(open_time / (5 * 3600000)), symbol_kline " +
//            "ORDER BY open_time")
//    List<Kline> findDataNewInterval(@Param("symbolKline") String symbolKline, @Param("startTime") Long startTime, @Param("endTime") Long endTime);
@Select("SELECT " +
        "FLOOR(open_time / (5 * 3600000)) * (5 * 3600000) AS open_time, " +
        "MAX(close_time) as close_time, " +
        "FIRST_VALUE(open_price) OVER (PARTITION BY FLOOR(open_time / (5 * 3600000)) ORDER BY open_time) as open_price, " +
        "MAX(high_price) as high_price, " +
        "MIN(low_price) as low_price, " +
        "LAST_VALUE(close_price) OVER (PARTITION BY FLOOR(open_time / (5 * 3600000)) ORDER BY open_time DESC) as close_price, " +
        "SUM(volume) as volume, " +
        "SUM(quote_asset_volume) as quote_asset_volume, " +
        "SUM(number_of_trades) as number_of_trades, " +
        "SUM(taker_buy_base_asset_volume) as taker_buy_base_asset_volume, " +
        "SUM(taker_buy_quote_asset_volume) as taker_buy_quote_asset_volume, " +
        "symbol_kline " +
        "FROM UIKlines " +
        "WHERE symbol_kline = #{symbolKline} AND open_time BETWEEN #{startTime} AND #{endTime} " +
        "GROUP BY FLOOR(open_time / (5 * 3600000)), symbol_kline " +
        "ORDER BY open_time")
List<Kline> findDataNewInterval(@Param("symbolKline") String symbolKline, @Param("startTime") Long startTime, @Param("endTime") Long endTime);

    @Select("SELECT * FROM UIKlines WHERE symbol_kline = #{symbolKline} AND open_time = #{openTime}")
    List<Kline> findBySymbolAndOpenTime(@Param("symbolKline") String symbolKline, @Param("openTime") Long openTime);

    @Update("UPDATE UIKlines SET close_time = #{closeTime}, open_price = #{openPrice}, high_price = #{highPrice}, low_price = #{lowPrice}, close_price = #{closePrice}, volume = #{volume}, quote_asset_volume = #{quoteAssetVolume}, number_of_trades = #{numberOfTrades}, taker_buy_base_asset_volume = #{takerBuyBaseAssetVolume}, taker_buy_quote_asset_volume = #{takerBuyQuoteAssetVolume} " +
            "WHERE symbol_kline = #{symbolKline} AND open_time = #{openTime}")
    int updateKline(Kline kline);

    @Delete("DELETE FROM UIKlines WHERE symbol_kline = #{symbolKline} AND open_time = #{openTime}")
    void deleteBySymbolAndOpenTime(@Param("symbolKline") String symbolKline, @Param("openTime") Long openTime);

    @Select("SELECT * FROM UIKlines WHERE symbol_kline = #{symbolKline} ORDER BY open_time DESC LIMIT #{limit}")
    List<Kline> findRecentBySymbol(@Param("symbolKline") String symbolKline, @Param("limit") int limit);
}

