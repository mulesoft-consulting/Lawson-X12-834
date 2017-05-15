%dw 1.0
%output application/java
---
{
	TransactionSets: {
		v005010: {
			"834": [{
				Interchange: {
					ISA01: "00",
					ISA03: "00"
				},
				SetHeader: {
					ST01: payload.STA0.ID as :string,
					ST02: payload.STA0.CN as :string
				},
				Heading: {
					"0200_BGN": {
						BGN01: addPad(payload.BGN0."Purpose Code", 2),
						BGN02: payload.BGN0."Identifier Code",
						BGN03: payload.BGN0."Creation Date" as :string as :date {format: "yyyyMMdd"},
						BGN04: payload.BGN0."Creation Time",
						BGN05: payload.BGN0."Time Zone Code" default "CS",
						BGN06: payload.BGN0."Identifier Code 2" default "BGN-6 should be supplied",
						BGN08: payload.BGN0."Action Code" as :string
					},
					"0400_DTP": [{
						DTP01: "QWE" as :string,
						DTP02: payload.DTP0."Format Qualifier",
						DTP03: payload.DTP0."Date Time" as :string
					}],
					"1000_Loop": [{
						"0700_N1": {
							N101: payload.N1A0."Plan Sponsor",
							N102: payload.N1A0."Plan Sponsor Name",
							N103: payload.N1A0."Identification Code Qualifier",
							N104: payload.N1A0."Identification Code"
						}
					},
					{
						"0700_N1": {
							N101: payload.N1B0."Identifier Code",
							N102: payload.N1B0."Insurer Name",
							N103: payload.N1B0."Identification Code Qualifier",
							N104: payload.N1B0."Identifier Code"
						}
					},
					({
						"0700_N1": {
							N101: payload.N1C0."Identifier Code",
							N102: payload.N1C0."Insurer Name",
							N103: payload.N1C0."Identification Code Qualifier",
							N104: payload.N1C0."Identifier Code"
						}
					}) when payload.N1C0?
					]
				},
				Detail: {
					"2000_Loop": payload.Data map ((datum , indexOfDatum) -> {
						
						"0100_INS": {
							INS01: datum.INS0."Insured Indicator",
							INS02: addPad(datum.INS0."Relationship Code",2),
							INS03: addPad(datum.INS0."Maintenance Type Code",3),
							INS04: datum.INS0."Maintenance Reason Code",
							INS05: datum.INS0."Benefits Status Code",
							INS0601: datum.INS0."Medicare Plan Code",
							INS0602: datum.INS0."COBRA Code" as :string when datum.INS0."COBRA Code" != null otherwise " ",
							INS08: datum.INS0."Employment Status  Code" when datum.INS0."Employment Status  Code" != null otherwise "QW",
							INS09: datum.INS0."Student Status Code" when datum.INS0."Student Status Code" != null otherwise "N"
						},
						"0200_REF": [{
							REF01: datum.REF0."Ref Application Qualifier",
							REF02: datum.REF0."Subscriber Identifier",
							REF0401: datum.REF1."Ref Application Qualifier",
							REF0402: datum.REF1."Subscriber Identifier" when datum.REF1."Subscriber Identifier" != null otherwise "SSN-SS-NSSN"
						}],
						"0250_DTP": [{
							DTP01: datum.DTP1."Date Qualifier" as :string when datum.DTP1."Date Qualifier" != null otherwise addPad(123,3),
							DTP02: datum.DTP1."Format Identifier" when datum.DTP1."Format Identifier" != null otherwise "QW",
							DTP03: datum.DTP1."Status Date" as :string when datum.DTP1."Status Date" != null otherwise addPad(19710101,8)
						}],
						"2100_Loop": [{
							"0300_NM1": {
								NM101: datum.NM10."Entity Identifier",
								NM102: datum.NM10."Type Qualifier" as :string,
								NM103: datum.NM10."Subscriber Last Name",
								NM104: datum.NM10."Subscriber First Name",
								NM105: datum.NM10."Subscriber Middle Name" when datum.NM10."Subscriber Middle Name" != null otherwise "",
								NM106: datum.NM10."Subscriber Prefix" when datum.NM10."Subscriber Prefix" != null otherwise "",
								NM107: datum.NM10."Subscriber Suffix" when datum.NM10."Subscriber Suffix" != null otherwise "    ",
								NM108: datum.NM10."Identification Code Qualifier",
								NM109: datum.NM10."Subscriber Identifier"
							},
							"0500_N3": {
								N301: datum.N3A0.Address1,
								N302: datum.N3A0.Address2 when datum.N3A0.Address2 != null otherwise ""
							},
							"0600_N4": {
								N401: datum.N4A0.City when datum.N4A0.City != null otherwise addPad("",18),
								N402: datum.N4A0.State when datum.N4A0.State != null otherwise  addPad("",2),
								N403: datum.N4A0.Zip when datum.N4A0.Zip != null otherwise  addPad("",10),
								N404: datum.N4A0."Country Code" when datum.N4A0."Country Code" != null otherwise  addPad("",2)
							},
							"0800_DMG": {
								DMG01: datum.DMG0."Date Qualifier",
								DMG02: datum.DMG0."Birth Date" as :string,
								DMG03: datum.DMG0.Gender,
								DMG04: datum.DMG0."MArrital Status" when datum.DMG0."MArrital Status" != null otherwise "U"
							}
						}],
						"2300_Loop": [{
							"2600_HD": {
								HD01: addPad(datum.HDA0."Maintenance Type" , 3),
								HD03: datum.HDA0."Insurance Line",
								HD04: datum.HDA0."Coverage Description",
								HD05: datum.HDA0."Level Code" when datum.HDA0."Level Code" != null otherwise "QWE"
							},
							"2700_DTP": [{
								DTP01: datum.DTP2."Time Qualifier" as :string,
								DTP02: datum.DTP2."Date Qualifier",
								DTP03: datum.DTP2."Coverage Period" as :string
							}]
						}]
						
						
					})
				}
			}]
		}
	}
}