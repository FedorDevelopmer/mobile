//
//  ItemsView.swift
//  lab1
//
//  Created by fedor on 2.03.24.
//

import Foundation
import SwiftUI

struct ItemsView : View {
    @EnvironmentObject var appPage: PageState
    @EnvironmentObject var auth: AuthState
    @State var ssdArray:Array<SSD>
    
    
    var body : some View {
        Text("Header").padding().onAppear{
            SSD.getItemsFromDB(){(array) in
                ssdArray = array
            }
        }
        List(ssdArray){ item in
            Text(String(item.getModel()))
        }
        Text("Hugo").padding()
        Button(action:{
            //appPage.page = PageEnum.AUTH
            //Dat().addTestData()
            //SSD.getItemsFromDB()
        },label: {Text("Back")})
    }
}

struct Item : View{
    var element: SSD
    
    var body: some View{
        
            Text(verbatim: String(element.getModel()))
            Text(verbatim: String(element.getPrice()))
    }
    
    public init(item:SSD){
        self.element = item
    }
}
