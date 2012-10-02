package com.karthik.wext.configs;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class VendorMapping {
	public static final Map<Vendor, LinkedHashSet<SiteName>> VENDOR_SITE = new LinkedHashMap<Vendor, LinkedHashSet<SiteName>>() {

		{
			put(Vendor.V1_BigPond, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.S1_BigPond_Movies);
					add(SiteName.S2_BigPond_TV_Shows);
				}
			});

			put(Vendor.V2_LoveFilm, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.S3_LoveFilm);

				}
			});
			put(Vendor.V3_CanalPlay, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.S4_CanalPlay);

				}
			});
			put(Vendor.V4_PortugalTelecomSGPS, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.S5_PORTUGAL_TELECOM);

				}
			});
			put(Vendor.V5_TelecomItaliaSpA, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.S6_Telecom_Italia);

				}
			});
			put(Vendor.V6_VTRGlobalComS, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.S7_VTR_GlobalCom_SA);

				}
			});
			put(Vendor.V7_SingTel, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.S8_SingTel);

				}
			});
			put(Vendor.V8_UPCPolska, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.S9_UPC_Polska_M);

				}
			});
			put(Vendor.V9_ManitobaTelecom, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.S10_MTS);

				}
			});
			put(Vendor.V10_LattelecomGroup, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.S11_Lattelecom_Group);

				}
			});
			put(Vendor.V11_TelmexColombiaSA, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.S12_Telmex_Colom);

				}
			});
			put(Vendor.V12_ShawCablesystems, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.S13_Shaw_Cable_Movie);
					add(SiteName.S15_Shaw_Cable_Subsc);

				}
			});
			put(Vendor.V13_RogersCable, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.S16_Roger_All);
					add(SiteName.S17_Roger_Movies_Free_for_Everyone);
					add(SiteName.S18_Roger_Movies_Free_from_Rgr_Cust);
					add(SiteName.S19_Roger_All_TV);
					add(SiteName.S20_Roger_TV_Free_for_Everyone);
					add(SiteName.S21_Roger_TV_Free_from_Rgr_Cust);

				}
			});
			put(Vendor.V14_Netflix, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.S23_Netflix);

				}
			});
			put(Vendor.V15_Hulu, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.S24_Non_Hulu_Plus);
					add(SiteName.S25_Hulu_Plus_Movies);
					add(SiteName.S26_Hulu_All_Movies);
					add(SiteName.S27_Non_Hulu_Plus_TV_Shows);
					add(SiteName.S28_Hulu_Plus_TV_Shows);
					add(SiteName.S29_Hulu_All_TV_Shows);
				}
			});

			// ================PART2===========================

			put(Vendor.V16_Youkus, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V16_1_YoukuTV);
					add(SiteName.V16_2_YoukuMovie);
				}
			});

			put(Vendor.V17_Xuniel, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V17_1_XunleiByGenre);
					add(SiteName.V17_2_Xunlei_ByArea);
					add(SiteName.V17_3_Xunlei_ByYear);
				}
			});
			put(Vendor.V18_Maxdome, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V18_1_Maxdom_Musik);
					add(SiteName.V18_2_Maxdom_KIDS);
					add(SiteName.V18_3_Maxdom_WissenDocu);
					add(SiteName.V18_4_Maxdome_ShowsComedy);
					add(SiteName.V18_5_Maxdome_Serie);
					add(SiteName.V18_6_Maxdome_Spielfilme);

				}
			});
			put(Vendor.V19_ITI_Neovision, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V19_1_ITINeovision);
				}
			});
			put(Vendor.V20_Belgacom, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V20_1_Belgacom);
				}
			});
			put(Vendor.V21_TiVo, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V21_1_Tivo_TV);
					add(SiteName.V21_2_Tivo_Movies);
				}
			});
			put(Vendor.V22_SkyNZ, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V22_1_SkyNZ_Sport);
					add(SiteName.V22_2_SkyNZ_RentalTV);
					add(SiteName.V22_3_SkyNZ_RentalMovies);
					add(SiteName.V22_4_SkyNZ_CatchTV);
					add(SiteName.V22_5_SkyNZ_CatchMovies);
				}
			});
			put(Vendor.V23_Numericable, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V23_1_Numericable);

				}
			});
			put(Vendor.V24_VoYo, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V24_1_VoYO);
					add(SiteName.V24_2_VoYo);

				}
			});
			put(Vendor.V25_Elion, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V25_1_Elion);
				}
			});
			put(Vendor.V26_Filmotech, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V26_1_Filmotech);

				}
			});
			put(Vendor.V27_YouSee, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V27_1_YouSee);

				}
			});
			put(Vendor.V28_DeutscheTelekom, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V28_1_DeutscheTelekom_Kids);
					add(SiteName.V28_2_DeutscheTelekom_Musik);
					add(SiteName.V28_3_DeutscheTelekom_Document);
					add(SiteName.V28_4_DeutscheTelekom_Serien);
					add(SiteName.V28_5_DeutscheTelekom_Filme);
				}
			});
			put(Vendor.V29_UPC_Austria, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V29_1_UPCAustria);
				}
			});

			put(Vendor.V30_FranceTelecom, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V30_1_FranceTelecom_Jeumesse);
					add(SiteName.V30_2_FranceTelecom_Musique);
					add(SiteName.V30_3_FranceTelecom_Documentaries);
					add(SiteName.V30_4_FranceTelecom_Humour);
					add(SiteName.V30_5_FranceTelecom_Series);
					add(SiteName.V30_6_FranceTelecom_Movies);

				}
			});
			put(Vendor.V31_TelecomnItalia, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V31_1_TelecomnItalia);
				}
			});
			put(Vendor.V32_TerraTV, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V32_1_TerraTV_VDEOCLUBE);
					add(SiteName.V32_2_TERRATV_ALUGUEL);
				}
			});
			put(Vendor.V33_LoveFilm, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V33_1_LoveFilm);
				}
			});
			put(Vendor.V34_TDC, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V34_1_TDC);
				}
			});
			put(Vendor.V35_ShawCable, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V35_1_ShawCable_Subs);
					add(SiteName.V35_2_ShawCable_Movies);
				}
			});

			put(Vendor.V36_CanalPlay, new LinkedHashSet<SiteName>() {
				{
					add(SiteName.V36_1_CanalPlay);
				}
			});

		}
	};

}
