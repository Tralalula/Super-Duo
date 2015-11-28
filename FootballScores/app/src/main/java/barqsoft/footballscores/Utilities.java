package barqsoft.footballscores;

import android.content.Context;

import barqsoft.footballscores.data.FootballScoresDatabaseContract;

public class Utilities {
    private static Context mContext;
    public static final int TWENTY_FOUR_HOURS_IN_MILLISECONDS = 86400000;

    // League codes
    public static final int DUMMY_LEAGUE = 357;
    public static final int BUNDESLIGA1 = 394;
    public static final int BUNDESLIGA2 = 395;
    public static final int LIGUE1 = 396;
    public static final int LIGUE2 = 397;
    public static final int PREMIER_LEAGUE = 398;
    public static final int PRIMERA_DIVISION = 399;
    public static final int SEGUNDA_DIVISION = 400;
    public static final int SERIE_A = 401;
    public static final int PRIMERA_LIGA = 402;
    public static final int BUNDESLIGA3 = 403;
    public static final int EREDIVISTE = 404;
    public static final int CHAMPIONS_LEAGUE = 405;

    public static final String[] DATABASE_PROJECTION = {
            FootballScoresDatabaseContract.ScoresTable._ID,
            FootballScoresDatabaseContract.ScoresTable.DATE_COL,
            FootballScoresDatabaseContract.ScoresTable.TIME_COL,
            FootballScoresDatabaseContract.ScoresTable.HOME_COL,
            FootballScoresDatabaseContract.ScoresTable.AWAY_COL,
            FootballScoresDatabaseContract.ScoresTable.LEAGUE_COL,
            FootballScoresDatabaseContract.ScoresTable.HOME_GOALS_COL,
            FootballScoresDatabaseContract.ScoresTable.AWAY_GOALS_COL,
            FootballScoresDatabaseContract.ScoresTable.MATCH_ID,
            FootballScoresDatabaseContract.ScoresTable.MATCH_DAY
    };

    public static final int DATABASE__ID = 0;
    public static final int DATABASE_DATE_COL = 1;
    public static final int DATABASE_TIME_COL = 2;
    public static final int DATABASE_HOME_COL = 3;
    public static final int DATABASE_AWAY_COL = 4;
    public static final int DATABASE_LEAGUE_COL = 5;
    public static final int DATABASE_HOME_GOALS_COL = 6;
    public static final int DATABASE_AWAY_GOALS_COL = 7;
    public static final int DATABASE_MATCH_ID_COL = 8;
    public static final int DATABASE_MATCH_DAY_COL = 9;

    public Utilities(Context context) {
        mContext = context;
    }

    private static String getResString(int id) {
        return mContext.getResources().getString(id);
    }

    public static String getLeague(int leagueNum) {
        // Using 'if' instead of 'switch' because using 'switch' will give ma 'constant expression required' error.
        if (leagueNum == DUMMY_LEAGUE) {
            return getResString(R.string.league_name_dummy_league);
        } else if (leagueNum == BUNDESLIGA1) {
            return getResString(R.string.league_name_bundesliga1);
        } else if (leagueNum == BUNDESLIGA2) {
            return getResString(R.string.league_name_bundesliga2);
        } else if (leagueNum == LIGUE1) {
            return getResString(R.string.league_name_ligue1);
        } else if (leagueNum == LIGUE2) {
            return getResString(R.string.league_name_ligue2);
        } else if (leagueNum == PREMIER_LEAGUE) {
            return getResString(R.string.league_name_premier_league);
        } else if (leagueNum == PRIMERA_DIVISION) {
            return getResString(R.string.league_name_primera_division);
        } else if (leagueNum == SEGUNDA_DIVISION) {
            return getResString(R.string.league_name_segunda_division);
        } else if (leagueNum == SERIE_A) {
            return getResString(R.string.league_name_serie_a);
        } else if (leagueNum == PRIMERA_LIGA) {
            return getResString(R.string.league_name_primera_liga);
        } else if (leagueNum == BUNDESLIGA3) {
            return getResString(R.string.league_name_bundesliga3);
        } else if (leagueNum == EREDIVISTE) {
            return getResString(R.string.league_name_erediviste);
        } else if (leagueNum == CHAMPIONS_LEAGUE) {
            return getResString(R.string.league_name_champions_league);
        } else {
            return getResString(R.string.league_not_known);
        }
    }

    public static String getMatchDay(int matchDay, int leagueNum) {
        if (leagueNum == CHAMPIONS_LEAGUE) {
            if (matchDay <= 6) {
                return getResString(R.string.match_day_champions_league_group_stages);
            } else if (matchDay == 7 || matchDay == 8) {
                return getResString(R.string.match_day_champions_league_first_knockout_round);
            } else if (matchDay == 9 || matchDay == 10) {
                return getResString(R.string.match_day_champions_league_quarter_finals);
            } else if (matchDay == 11 || matchDay == 12) {
                return getResString(R.string.match_day_champions_league_semi_finals);
            } else {
                return getResString(R.string.match_day_champions_league_finals);
            }
        } else {
            return getResString(R.string.match_day_default) + " " + String.valueOf(matchDay);
        }
    }

    public static String getScores(int homeGoals, int awayGoals) {
        if (homeGoals < 0 || awayGoals < 0) {
            return " - ";
        } else {
            return String.valueOf(homeGoals) + " - " + String.valueOf(awayGoals);
        }
    }

    public static int getTeamCrestByTeamName(String teamName) {
        // Using 'if' instead of 'switch' because using 'switch' will give ma 'constant expression required' error.
        if (teamName == null) {
            return R.drawable.no_icon;
        }

        if (teamName.equals(getResString(R.string.team_name_first_fc_heidenheim_1846))) {
            return R.drawable.first_fc_heidenheim_1846;
        } else if (teamName.equals(getResString(R.string.team_name_first_fc_kaiserslautern))) {
            return R.drawable.first_fc_kaiserslautern;
        } else if (teamName.equals(getResString(R.string.team_name_first_fc_koln))) {
            return R.drawable.first_fc_koln;
        } else if (teamName.equals(getResString(R.string.team_name_first_fc_nurnberg))) {
            return R.drawable.first_fc_nurnberg;
        } else if (teamName.equals(getResString(R.string.team_name_first_fc_union_berlin))) {
            return R.drawable.first_fc_union_berlin;
        } else if (teamName.equals(getResString(R.string.team_name_first_fsv_mainz_05))) {
            return R.drawable.mainz_05_ii;
        } else if (teamName.equals(getResString(R.string.team_name_ac_chievo_verona))) {
            return R.drawable.ac_chievo_verona;
        } else if (teamName.equals(getResString(R.string.team_name_ac_milan))) {
            return R.drawable.ac_milan;
        } else if (teamName.equals(getResString(R.string.team_name_acf_fiorentina))) {
            return R.drawable.acf_fiorentina;
        } else if (teamName.equals(getResString(R.string.team_name_ad_alcorcon))) {
            return R.drawable.ad_alcorcon;
        } else if (teamName.equals(getResString(R.string.team_name_ado_den_haag))) {
            return R.drawable.ado_den_haag;
        } else if (teamName.equals(getResString(R.string.team_name_afc_bournemouth))) {
            return R.drawable.afc_bournemouth;
        } else if (teamName.equals(getResString(R.string.team_name_aj_auxerre))) {
            return R.drawable.aj_auxerre;
        } else if (teamName.equals(getResString(R.string.team_name_as_monaco_fc))) {
            return R.drawable.as_monaco_fc;
        } else if (teamName.equals(getResString(R.string.team_name_as_nancy))) {
            return R.drawable.as_nancy;
        } else if (teamName.equals(getResString(R.string.team_name_as_roma))) {
            return R.drawable.as_roma;
        } else if (teamName.equals(getResString(R.string.team_name_as_roma))) {
            return R.drawable.as_roma;
        } else if (teamName.equals(getResString(R.string.team_name_as_saint_etienne))) {
            return R.drawable.as_saint_etienne;
        } else if (teamName.equals(getResString(R.string.team_name_az_alkmaar))) {
            return R.drawable.az_alkmaar;
        } else if (teamName.equals(getResString(R.string.team_name_academica_de_coimbra))) {
            return R.drawable.academica_de_coimbra;
        } else if (teamName.equals(getResString(R.string.team_name_ajaccio_ac))) {
            return R.drawable.ajaccio_ac;
        } else if (teamName.equals(getResString(R.string.team_name_ajax_amsterdam))) {
            return R.drawable.ajax_amsterdam;
        } else if (teamName.equals(getResString(R.string.team_name_angers_sco))) {
            return R.drawable.angers_sco;
        } else if (teamName.equals(getResString(R.string.team_name_arminia_bielefeld))) {
            return R.drawable.arminia_bielefeld;
        } else if (teamName.equals(getResString(R.string.team_name_arsenal_fc))) {
            return R.drawable.arsenal_fc;
        } else if (teamName.equals(getResString(R.string.team_name_arsenal_fc))) {
            return R.drawable.arsenal_fc;
        } else if (teamName.equals(getResString(R.string.team_name_aston_villa_fc))) {
            return R.drawable.aston_villa_fc;
        } else if (teamName.equals(getResString(R.string.team_name_atalanta_bc))) {
            return R.drawable.atalanta_bc;
        } else if (teamName.equals(getResString(R.string.team_name_athletic_bilbao_b))) {
            return R.drawable.athletic_bilbao_b;
        } else if (teamName.equals(getResString(R.string.team_name_athletic_club))) {
            return R.drawable.athletic_club;
        } else if (teamName.equals(getResString(R.string.team_name_bayer_leverkusen))) {
            return R.drawable.bayer_leverkusen;
        } else if (teamName.equals(getResString(R.string.team_name_bayer_leverkusen))) {
            return R.drawable.bayer_leverkusen;
        } else if (teamName.equals(getResString(R.string.team_name_belenenses_lissabon))) {
            return R.drawable.belenenses_lissabon;
        } else if (teamName.equals(getResString(R.string.team_name_benfica_lissabon))) {
            return R.drawable.benfica_lissabon;
        } else if (teamName.equals(getResString(R.string.team_name_benfica_lissabon))) {
            return R.drawable.benfica_lissabon;
        } else if (teamName.equals(getResString(R.string.team_name_boavista_porto_fc))) {
            return R.drawable.boavista_porto_fc;
        } else if (teamName.equals(getResString(R.string.team_name_bologna_fc))) {
            return R.drawable.bologna_fc;
        } else if (teamName.equals(getResString(R.string.team_name_bor_monchengladbach))) {
            return R.drawable.bor_monchengladbach;
        } else if (teamName.equals(getResString(R.string.team_name_borussia_dortmund))) {
            return R.drawable.borussia_dortmund;
        } else if (teamName.equals(getResString(R.string.team_name_ca_osasuna))) {
            return R.drawable.ca_osasuna;
        } else if (teamName.equals(getResString(R.string.team_name_cd_leganes))) {
            return R.drawable.cd_leganes;
        } else if (teamName.equals(getResString(R.string.team_name_cd_lugo))) {
            return R.drawable.cd_lugo;
        } else if (teamName.equals(getResString(R.string.team_name_cd_mirandes))) {
            return R.drawable.cd_mirandes;
        } else if (teamName.equals(getResString(R.string.team_name_cd_numancia_de_soria))) {
            return R.drawable.cd_numancia_de_soria;
        } else if (teamName.equals(getResString(R.string.team_name_cd_tenerife))) {
            return R.drawable.cd_tenerife;
        } else if (teamName.equals(getResString(R.string.team_name_cd_tondela))) {
            return R.drawable.cd_tondela;
        } else if (teamName.equals(getResString(R.string.team_name_cska_moscow))) {
            return R.drawable.cska_moscow;
        } else if (teamName.equals(getResString(R.string.team_name_carpi_fc))) {
            return R.drawable.carpi_fc;
        } else if (teamName.equals(getResString(R.string.team_name_chamois_niortais_fc))) {
            return R.drawable.chamois_niortais_fc;
        } else if (teamName.equals(getResString(R.string.team_name_chelsea_fc))) {
            return R.drawable.chelsea;
        } else if (teamName.equals(getResString(R.string.team_name_chemnitzer_fc))) {
            return R.drawable.chemnitzer_fc;
        } else if (teamName.equals(getResString(R.string.team_name_clermont_foot_auvergne))) {
            return R.drawable.clermont_foot_auvergne;
        } else if (teamName.equals(getResString(R.string.team_name_club_atletico_de_madrid))) {
            return R.drawable.club_atletico_de_madrid;
        } else if (teamName.equals(getResString(R.string.team_name_crystal_palace_fc))) {
            return R.drawable.crystal_palace_fc;
        } else if (teamName.equals(getResString(R.string.team_name_cordoba_cf))) {
            return R.drawable.cordoba_cf;
        } else if (teamName.equals(getResString(R.string.team_name_deportivo_alaves))) {
            return R.drawable.deportivo_alaves;
        } else if (teamName.equals(getResString(R.string.team_name_dijon_fco))) {
            return R.drawable.dijon_fco;
        } else if (teamName.equals(getResString(R.string.team_name_dynamo_dresden))) {
            return R.drawable.dynamo_dresden;
        } else if (teamName.equals(getResString(R.string.team_name_dynamo_kyiv))) {
            return R.drawable.dynamo_kyiv;
        } else if (teamName.equals(getResString(R.string.team_name_ea_guingamp))) {
            return R.drawable.ea_guingamp;
        } else if (teamName.equals(getResString(R.string.team_name_es_troyes_ac))) {
            return R.drawable.es_troyes_ac;
        } else if (teamName.equals(getResString(R.string.team_name_eintracht_braunschweig))) {
            return R.drawable.eintracht_braunschweig;
        } else if (teamName.equals(getResString(R.string.team_name_eintracht_frankfurt))) {
            return R.drawable.eintracht_frankfurt;
        } else if (teamName.equals(getResString(R.string.team_name_elche_fc))) {
            return R.drawable.elche_fc;
        } else if (teamName.equals(getResString(R.string.team_name_empoli_fc))) {
            return R.drawable.empoli_fc;
        } else if (teamName.equals(getResString(R.string.team_name_energie_cottbus))) {
            return R.drawable.energie_cottbus;
        } else if (teamName.equals(getResString(R.string.team_name_erzgebirge_aue))) {
            return R.drawable.erzgebirge_aue;
        } else if (teamName.equals(getResString(R.string.team_name_everton_fc))) {
            return R.drawable.everton_fc;
        } else if (teamName.equals(getResString(R.string.team_name_excelsior))) {
            return R.drawable.excelsior;
        } else if (teamName.equals(getResString(R.string.team_name_fc_arouca))) {
            return R.drawable.fc_arouca;
        } else if (teamName.equals(getResString(R.string.team_name_fc_astana))) {
            return R.drawable.fc_astana;
        } else if (teamName.equals(getResString(R.string.team_name_fc_augsburg))) {
            return R.drawable.fc_augsburg;
        } else if (teamName.equals(getResString(R.string.team_name_fc_barcelona))) {
            return R.drawable.fc_barcelona;
        } else if (teamName.equals(getResString(R.string.team_name_fc_barcelona))) {
            return R.drawable.fc_barcelona;
        } else if (teamName.equals(getResString(R.string.team_name_fc_bayern_munchen))) {
            return R.drawable.fc_bayern_munchen;
        } else if (teamName.equals(getResString(R.string.team_name_fc_girondins_de_bordeaux))) {
            return R.drawable.fc_girondins_de_bordeaux;
        } else if (teamName.equals(getResString(R.string.team_name_fc_groningen))) {
            return R.drawable.fc_groningen;
        } else if (teamName.equals(getResString(R.string.team_name_fc_hansa_rostock))) {
            return R.drawable.fc_hansa_rostock;
        } else if (teamName.equals(getResString(R.string.team_name_fc_ingolstadt_04))) {
            return R.drawable.fc_ingolstadt_04;
        } else if (teamName.equals(getResString(R.string.team_name_fc_internazionale_milano))) {
            return R.drawable.fc_internazionale_milano;
        } else if (teamName.equals(getResString(R.string.team_name_fc_lorient))) {
            return R.drawable.fc_lorient;
        } else if (teamName.equals(getResString(R.string.team_name_fc_metz))) {
            return R.drawable.fc_metz;
        } else if (teamName.equals(getResString(R.string.team_name_fc_nantes))) {
            return R.drawable.fc_nantes;
        } else if (teamName.equals(getResString(R.string.team_name_fc_pacos_de_ferreira))) {
            return R.drawable.fc_pacos_de_ferreira;
        } else if (teamName.equals(getResString(R.string.team_name_fc_porto))) {
            return R.drawable.fc_porto;
        } else if (teamName.equals(getResString(R.string.team_name_fc_porto))) {
            return R.drawable.fc_porto;
        } else if (teamName.equals(getResString(R.string.team_name_fc_rio_ave))) {
            return R.drawable.fc_rio_ave;
        } else if (teamName.equals(getResString(R.string.team_name_fc_schalke_04))) {
            return R.drawable.fc_schalke_04;
        } else if (teamName.equals(getResString(R.string.team_name_fc_st_pauli))) {
            return R.drawable.fc_st_pauli;
        } else if (teamName.equals(getResString(R.string.team_name_fc_stade_lavallois_mayenne))) {
            return R.drawable.fc_stade_lavallois_mayenne;
        } else if (teamName.equals(getResString(R.string.team_name_fc_twente_enschede))) {
            return R.drawable.fc_twente_enschede;
        } else if (teamName.equals(getResString(R.string.team_name_fc_utrecht))) {
            return R.drawable.fc_utrecht;
        } else if (teamName.equals(getResString(R.string.team_name_fc_valenciennes))) {
            return R.drawable.fc_valenciennes;
        } else if (teamName.equals(getResString(R.string.team_name_fc_zenit_st_petersburg))) {
            return R.drawable.fc_zenit_st_petersburg;
        } else if (teamName.equals(getResString(R.string.team_name_fk_bate_baryssau))) {
            return R.drawable.fk_bate_baryssau;
        } else if (teamName.equals(getResString(R.string.team_name_fsv_frankfurt))) {
            return R.drawable.fsv_frankfurt;
        } else if (teamName.equals(getResString(R.string.team_name_feyenoord_rotterdam))) {
            return R.drawable.feyenoord_rotterdam;
        } else if (teamName.equals(getResString(R.string.team_name_fortuna_dusseldorf))) {
            return R.drawable.fortuna_dusseldorf;
        } else if (teamName.equals(getResString(R.string.team_name_fortuna_koln))) {
            return R.drawable.fortuna_koln;
        } else if (teamName.equals(getResString(R.string.team_name_frosinone_calcio))) {
            return R.drawable.frosinone_calcio;
        } else if (teamName.equals(getResString(R.string.team_name_gd_estoril_praia))) {
            return R.drawable.gd_estoril_paria;
        } else if (teamName.equals(getResString(R.string.team_name_gnk_dinamo_zagreb))) {
            return R.drawable.gnk_dinamo_zagreb;
        } else if (teamName.equals(getResString(R.string.team_name_galatasaray_sk))) {
            return R.drawable.galatasaray_sk;
        } else if (teamName.equals(getResString(R.string.team_name_gazelec_ajaccio))) {
            return R.drawable.gazelec_ajaccio;
        } else if (teamName.equals(getResString(R.string.team_name_genoa_cfc))) {
            return R.drawable.genoa_cfc;
        } else if (teamName.equals(getResString(R.string.team_name_getafe_cf))) {
            return R.drawable.getafe_cf;
        } else if (teamName.equals(getResString(R.string.team_name_gimnastic_de_tarragona))) {
            return R.drawable.gimnastic_de_tarragona;
        } else if (teamName.equals(getResString(R.string.team_name_girona_fc))) {
            return R.drawable.girona_fc;
        } else if (teamName.equals(getResString(R.string.team_name_granada_cf))) {
            return R.drawable.granada_cf;
        } else if (teamName.equals(getResString(R.string.team_name_hallescher_fc))) {
            return R.drawable.hallescher_fc;
        } else if (teamName.equals(getResString(R.string.team_name_hamburger_sv))) {
            return R.drawable.hamburger_sv;
        } else if (teamName.equals(getResString(R.string.team_name_hannover_96))) {
            return R.drawable.hannover_96;
        } else if (teamName.equals(getResString(R.string.team_name_hellas_verona_fc))) {
            return R.drawable.hellas_verona_fc;
        } else if (teamName.equals(getResString(R.string.team_name_heracles_almelo))) {
            return R.drawable.heracles_almelo;
        } else if (teamName.equals(getResString(R.string.team_name_hertha_bsc))) {
            return R.drawable.hertha_bsc;
        } else if (teamName.equals(getResString(R.string.team_name_holstein_kiel))) {
            return R.drawable.holstein_kiel;
        } else if (teamName.equals(getResString(R.string.team_name_juventus_turin))) {
            return R.drawable.juventus_turin;
        } else if (teamName.equals(getResString(R.string.team_name_juventus_turin))) {
            return R.drawable.juventus_turin;
        } else if (teamName.equals(getResString(R.string.team_name_kaa_gent))) {
            return R.drawable.kaa_gent;
        } else if (teamName.equals(getResString(R.string.team_name_karlsruher_sc))) {
            return R.drawable.karlsruher_sc;
        } else if (teamName.equals(getResString(R.string.team_name_le_havre_ac))) {
            return R.drawable.le_havre_ac;
        } else if (teamName.equals(getResString(R.string.team_name_leicester_city_fc))) {
            return R.drawable.leicester_city_fc;
        } else if (teamName.equals(getResString(R.string.team_name_levante_ud))) {
            return R.drawable.levante_ud;
        } else if (teamName.equals(getResString(R.string.team_name_liverpool_fc))) {
            return R.drawable.liverpool_fc;
        } else if (teamName.equals(getResString(R.string.team_name_msv_duisburg))) {
            return R.drawable.msv_duisburg;
        } else if (teamName.equals(getResString(R.string.team_name_maccabi_tel_aviv))) {
            return R.drawable.maccabi_tel_aviv;
        } else if (teamName.equals(getResString(R.string.team_name_magdeburg))) {
            return R.drawable.magdeburg;
        } else if (teamName.equals(getResString(R.string.team_name_mainz_05_ii))) {
            return R.drawable.mainz_05_ii;
        } else if (teamName.equals(getResString(R.string.team_name_malmo_ff))) {
            return R.drawable.malmo_ff;
        } else if (teamName.equals(getResString(R.string.team_name_manchester_city_fc))) {
            return R.drawable.manchester_city_fc;
        } else if (teamName.equals(getResString(R.string.team_name_manchester_city_fc))) {
            return R.drawable.manchester_city_fc;
        } else if (teamName.equals(getResString(R.string.team_name_manchester_united_fc))) {
            return R.drawable.manchester_united_fc;
        } else if (teamName.equals(getResString(R.string.team_name_manchester_united_fc))) {
            return R.drawable.manchester_united_fc;
        } else if (teamName.equals(getResString(R.string.team_name_maritimo_funchal))) {
            return R.drawable.maritimo_funchal;
        } else if (teamName.equals(getResString(R.string.team_name_montpellier_herault_sc))) {
            return R.drawable.montpellier_herault_sc;
        } else if (teamName.equals(getResString(R.string.team_name_moreirense_fc))) {
            return R.drawable.moreirense_fc;
        } else if (teamName.equals(getResString(R.string.team_name_malaga_cf))) {
            return R.drawable.malaga_cf;
        } else if (teamName.equals(getResString(R.string.team_name_nec_nijmegen))) {
            return R.drawable.nec_nijmegen;
        } else if (teamName.equals(getResString(R.string.team_name_nacional_funchal))) {
            return R.drawable.nacional_funchal;
        } else if (teamName.equals(getResString(R.string.team_name_newcastle_united_fc))) {
            return R.drawable.newcastle_united_fc;
        } else if (teamName.equals(getResString(R.string.team_name_norwich_city_fc))) {
            return R.drawable.norwich_city_fc;
        } else if (teamName.equals(getResString(R.string.team_name_nimes_olympique))) {
            return R.drawable.nimes_olympique;
        } else if (teamName.equals(getResString(R.string.team_name_ogc_nice))) {
            return R.drawable.ogc_nice;
        } else if (teamName.equals(getResString(R.string.team_name_osc_lille))) {
            return R.drawable.osc_lille;
        } else if (teamName.equals(getResString(R.string.team_name_olympiacos_fc))) {
            return R.drawable.olympiacos_fc;
        } else if (teamName.equals(getResString(R.string.team_name_olympique_lyonnais))) {
            return R.drawable.olympique_lyonnais;
        } else if (teamName.equals(getResString(R.string.team_name_olympique_lyonnais))) {
            return R.drawable.olympique_lyonnais;
        } else if (teamName.equals(getResString(R.string.team_name_olympique_de_marseille))) {
            return R.drawable.olympique_de_marseille;
        } else if (teamName.equals(getResString(R.string.team_name_pec_zwolle))) {
            return R.drawable.pec_zwolle;
        } else if (teamName.equals(getResString(R.string.team_name_psv_eindhoven))) {
            return R.drawable.psv_eindhoven;
        } else if (teamName.equals(getResString(R.string.team_name_psv_eindhoven))) {
            return R.drawable.psv_eindhoven;
        } else if (teamName.equals(getResString(R.string.team_name_paris_fc))) {
            return R.drawable.paris_fc;
        } else if (teamName.equals(getResString(R.string.team_name_paris_saint_germain))) {
            return R.drawable.paris_saint_germain;
        } else if (teamName.equals(getResString(R.string.team_name_paris_saint_germain))) {
            return R.drawable.paris_saint_germain;
        } else if (teamName.equals(getResString(R.string.team_name_preuszen_munster))) {
            return R.drawable.preuszen_munster;
        } else if (teamName.equals(getResString(R.string.team_name_rc_celta_de_vigo))) {
            return R.drawable.rc_celta_de_vigo;
        } else if (teamName.equals(getResString(R.string.team_name_rc_deportivo_la_coruna))) {
            return R.drawable.rc_deportivo_la_coruna;
        } else if (teamName.equals(getResString(R.string.team_name_rc_lens))) {
            return R.drawable.rc_lens;
        } else if (teamName.equals(getResString(R.string.team_name_rc_tours))) {
            return R.drawable.rc_tours;
        } else if (teamName.equals(getResString(R.string.team_name_rcd_espanyol))) {
            return R.drawable.rcd_espanyol;
        } else if (teamName.equals(getResString(R.string.team_name_rcd_mallorca))) {
            return R.drawable.rcd_mallorca;
        } else if (teamName.equals(getResString(R.string.team_name_rayo_vallecano_de_madrid))) {
            return R.drawable.club_atletico_de_madrid;
        } else if (teamName.equals(getResString(R.string.team_name_real_betis))) {
            return R.drawable.real_betis;
        } else if (teamName.equals(getResString(R.string.team_name_real_madrid_cf))) {
            return R.drawable.real_madrid_cf;
        } else if (teamName.equals(getResString(R.string.team_name_real_madrid_cf))) {
            return R.drawable.real_madrid_cf;
        } else if (teamName.equals(getResString(R.string.team_name_real_oviedo))) {
            return R.drawable.real_oviedo;
        } else if (teamName.equals(getResString(R.string.team_name_real_sociedad_de_futbol))) {
            return R.drawable.real_sociedad_de_futbol;
        } else if (teamName.equals(getResString(R.string.team_name_real_valladolid))) {
            return R.drawable.real_valladolid;
        } else if (teamName.equals(getResString(R.string.team_name_real_zaragoza))) {
            return R.drawable.real_zaragoza;
        } else if (teamName.equals(getResString(R.string.team_name_red_bull_leipzig))) {
            return R.drawable.red_bull_leipzig;
        } else if (teamName.equals(getResString(R.string.team_name_red_star_93))) {
            return R.drawable.red_star_93;
        } else if (teamName.equals(getResString(R.string.team_name_roda_jc_kerkrade))) {
            return R.drawable.roda_jc_kerkrade;
        } else if (teamName.equals(getResString(R.string.team_name_rot_weisz_erfurt))) {
            return R.drawable.rot_weisz_erfurt;
        } else if (teamName.equals(getResString(R.string.team_name_sc_bastia))) {
            return R.drawable.sc_bastia;
        } else if (teamName.equals(getResString(R.string.team_name_sc_cambuur_leeuwarden))) {
            return R.drawable.sc_cambuur_leeuwarden;
        } else if (teamName.equals(getResString(R.string.team_name_sc_freiburg))) {
            return R.drawable.sc_freiburg;
        } else if (teamName.equals(getResString(R.string.team_name_sc_heerenveen))) {
            return R.drawable.sc_heerenveen;
        } else if (teamName.equals(getResString(R.string.team_name_sc_paderborn_07))) {
            return R.drawable.sc_paderborn_07;
        } else if (teamName.equals(getResString(R.string.team_name_sd_eibar))) {
            return R.drawable.sd_eibar;
        } else if (teamName.equals(getResString(R.string.team_name_sd_ponferradina))) {
            return R.drawable.sd_ponferradina;
        } else if (teamName.equals(getResString(R.string.team_name_sg_sonnenhof_groszaspach))) {
            return R.drawable.sg_sonnenhof_groszaspach;
        } else if (teamName.equals(getResString(R.string.team_name_sm_caen))) {
            return R.drawable.sm_caen;
        } else if (teamName.equals(getResString(R.string.team_name_ss_lazio))) {
            return R.drawable.ss_lazio;
        } else if (teamName.equals(getResString(R.string.team_name_ssc_napoli))) {
            return R.drawable.ssc_napoli;
        } else if (teamName.equals(getResString(R.string.team_name_sv_darmstadt_98))) {
            return R.drawable.sv_darmstadt_98;
        } else if (teamName.equals(getResString(R.string.team_name_sv_sandhausen))) {
            return R.drawable.sv_sandhausen;
        } else if (teamName.equals(getResString(R.string.team_name_sv_wehen_wiesbaden))) {
            return R.drawable.sv_wehen_wiesbaden;
        } else if (teamName.equals(getResString(R.string.team_name_sevilla_fc))) {
            return R.drawable.sevilla_fc;
        } else if (teamName.equals(getResString(R.string.team_name_sevilla_fc))) {
            return R.drawable.sevilla_fc;
        } else if (teamName.equals(getResString(R.string.team_name_shakhtar_donetsk))) {
            return R.drawable.shakhtar_donetsk;
        } else if (teamName.equals(getResString(R.string.team_name_sochaux_fc))) {
            return R.drawable.sochaux_fc;
        } else if (teamName.equals(getResString(R.string.team_name_southampton_fc))) {
            return R.drawable.southampton_fc;
        } else if (teamName.equals(getResString(R.string.team_name_spvgg_greuther_furth))) {
            return R.drawable.spvgg_greuther_furth;
        } else if (teamName.equals(getResString(R.string.team_name_sporting_braga))) {
            return R.drawable.sporting_braga;
        } else if (teamName.equals(getResString(R.string.team_name_sporting_gijon))) {
            return R.drawable.sporting_gijon;
        } else if (teamName.equals(getResString(R.string.team_name_sporting_lisbon))) {
            return R.drawable.sporting_lisbon;
        } else if (teamName.equals(getResString(R.string.team_name_stade_brestois))) {
            return R.drawable.stade_brestois;
        } else if (teamName.equals(getResString(R.string.team_name_stade_rennais_fc))) {
            return R.drawable.stade_rennais_fc;
        } else if (teamName.equals(getResString(R.string.team_name_stade_de_reims))) {
            return R.drawable.stade_de_reims;
        } else if (teamName.equals(getResString(R.string.team_name_stoke_city_fc))) {
            return R.drawable.stoke_city_fc;
        } else if (teamName.equals(getResString(R.string.team_name_stuttgarter_kickers))) {
            return R.drawable.stuttgarter_kickers;
        } else if (teamName.equals(getResString(R.string.team_name_sunderland_afc))) {
            return R.drawable.sunderland_afc;
        } else if (teamName.equals(getResString(R.string.team_name_swansea_city_fc))) {
            return R.drawable.swansea_city_fc;
        } else if (teamName.equals(getResString(R.string.team_name_tsg_1899_hoffenheim))) {
            return R.drawable.tsg_1899_hoffenheim;
        } else if (teamName.equals(getResString(R.string.team_name_tsv_1860_munchen))) {
            return R.drawable.tsv_1860_munchen;
        } else if (teamName.equals(getResString(R.string.team_name_torino_fc))) {
            return R.drawable.torino_fc;
        } else if (teamName.equals(getResString(R.string.team_name_tottenham_hotspur_fc))) {
            return R.drawable.tottenham_hotspur_fc;
        } else if (teamName.equals(getResString(R.string.team_name_toulouse_fc))) {
            return R.drawable.toulouse_fc;
        } else if (teamName.equals(getResString(R.string.team_name_uc_sampdoria))) {
            return R.drawable.uc_sampdoria;
        } else if (teamName.equals(getResString(R.string.team_name_ud_almeria))) {
            return R.drawable.ud_almeria;
        } else if (teamName.equals(getResString(R.string.team_name_ud_las_palmas))) {
            return R.drawable.ud_las_palmas;
        } else if (teamName.equals(getResString(R.string.team_name_ue_llagostera))) {
            return R.drawable.ue_llagostera;
        } else if (teamName.equals(getResString(R.string.team_name_us_citta_di_palermo))) {
            return R.drawable.us_citta_di_palermo;
        } else if (teamName.equals(getResString(R.string.team_name_us_creteil))) {
            return R.drawable.us_creteil;
        } else if (teamName.equals(getResString(R.string.team_name_us_sassuolo_calcio))) {
            return R.drawable.us_sassuolo_calcio;
        } else if (teamName.equals(getResString(R.string.team_name_udinese_calcio))) {
            return R.drawable.udinese_calcio;
        } else if (teamName.equals(getResString(R.string.team_name_uniao_madeira))) {
            return R.drawable.uniao_madeira;
        } else if (teamName.equals(getResString(R.string.team_name_valencia_cf))) {
            return R.drawable.valencia_fc;
        } else if (teamName.equals(getResString(R.string.team_name_vfb_stuttgart_ii))) {
            return R.drawable.vfb_stuttgart_ii;
        } else if (teamName.equals(getResString(R.string.team_name_vfl_bochum))) {
            return R.drawable.vfl_bochum;
        } else if (teamName.equals(getResString(R.string.team_name_vfl_osnabruck))) {
            return R.drawable.vfl_osnabruck;
        } else if (teamName.equals(getResString(R.string.team_name_vfr_aalen))) {
            return R.drawable.vfr_aalen;
        } else if (teamName.equals(getResString(R.string.team_name_villarreal_cf))) {
            return R.drawable.villarreal_cf;
        } else if (teamName.equals(getResString(R.string.team_name_vitesse_arnhem))) {
            return R.drawable.vitesse_arnhem;
        } else if (teamName.equals(getResString(R.string.team_name_vitoria_guimaraes))) {
            return R.drawable.vitoria_guimaraes;
        } else if (teamName.equals(getResString(R.string.team_name_vitoria_setubal))) {
            return R.drawable.vitoria_fc;
        } else if (teamName.equals(getResString(R.string.team_name_wurzburger_kickers))) {
            return R.drawable.wurzburg_kickers;
        } else if (teamName.equals(getResString(R.string.team_name_watford_fc))) {
            return R.drawable.watford_fc;
        } else if (teamName.equals(getResString(R.string.team_name_werder_bremen))) {
            return R.drawable.werder_bremen;
        } else if (teamName.equals(getResString(R.string.team_name_werder_bremen_ii))) {
            return R.drawable.werder_bremen_ii;
        } else if (teamName.equals(getResString(R.string.team_name_west_bromwich_albion_fc))) {
            return R.drawable.west_bromwich_albion_fc;
        } else if (teamName.equals(getResString(R.string.team_name_west_ham_united_fc))) {
            return R.drawable.west_ham_united_fc;
        } else if (teamName.equals(getResString(R.string.team_name_willem_ii))) {
            return R.drawable.willem_ii;
        } else if (teamName.equals(getResString(R.string.team_name_vfl_wolfsburg))) {
            return R.drawable.vfl_wolfsburg;
        } else if (teamName.equals(getResString(R.string.team_name_evian_thonon_gaillard_fc))) {
            return R.drawable.evian_thonon_gaillard_fc;
        } else if (teamName.equals(getResString(R.string.team_name_fc_bourg_en_bresse_peronnas))) {
            return R.drawable.fc_bourg_en_bresse_peronnas;
        } else if (teamName.equals(getResString(R.string.team_name_de_graafschap))) {
            return R.drawable.de_graafschap;
        } else if (teamName.equals(getResString(R.string.team_name_huesca))) {
            return R.drawable.huesca;
        } else {
            return R.drawable.no_icon;
        }
    }

    public static int inversePositionForRtl(int position, int total) {
        return total - position - 1;
    }
}
