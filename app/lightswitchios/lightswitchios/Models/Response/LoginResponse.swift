//
//  LoginResponse.swift
//  lightswitchios
//
//  Created by 김동훈 on 5/8/24.
//

import Foundation

struct LoginResponse: Decodable {
    let memberId: Int64
    let email: String
    let firstName: String
    let lastName: String
    let telNumber: String
}
