package com.demoboletto.repository;

import java.util.List;

/**
 * @author : hyunwoopark
 * @version : 1.0.0
 * @package : demo-boletto
 * @name : UserCustomRepository
 * @date : 2024. 12. 4. 오후 11:18
 * @modifyed : $
 **/
public interface UserCustomRepository {
    List<String> findDeviceTokensByUserIds(List<Long> userIds);

}
