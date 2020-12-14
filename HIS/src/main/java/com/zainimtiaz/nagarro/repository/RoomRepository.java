package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Branch;
import com.zainimtiaz.nagarro.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @author    : waqas kamran
 * @Date      : 17-Apr-18
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.*
 * @FileName  : UserAuthAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

void deleteAllByBranch(Branch branchList);
List<Room> findAllByBranch(Branch branches);


}

