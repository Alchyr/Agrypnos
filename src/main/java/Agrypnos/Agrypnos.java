package Agrypnos;

import Agrypnos.cards.Florist.*;
import Agrypnos.cards.Florist.Flowers.*;
import Agrypnos.util.CardColorEnum;
import Agrypnos.util.CharacterEnum;
import Agrypnos.characters.*;
import Agrypnos.relics.*;

import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@SpireInitializer
public class Agrypnos implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber,
        EditCharactersSubscriber, EditKeywordsSubscriber, PostInitializeSubscriber
{
    public static final Logger logger = LogManager.getLogger(Agrypnos.class.getSimpleName());

    // Colors

    public static final Color FLORIST_GREEN = CardHelper.getColor(129.0f, 112.0f, 30.0f);
    public static final Color FLORIST_ATTACK_HEART_PURPLE = CardHelper.getColor(229.0f, 211.0f, 84.0f);

    // Card backgrounds/basic images
    private static final String FLORIST_ATTACK_BACK = "character/Florist/CardGeneric/bg_attack.png";
    private static final String FLORIST_POWER_BACK = "character/Florist/CardGeneric/bg_power.png";
    private static final String FLORIST_SKILL_BACK = "character/Florist/CardGeneric/bg_skill.png";
    private static final String FLORIST_ENERGY_ORB = "character/Florist/CardGeneric/card_orb.png";
    private static final String FLORIST_CARD_ENERGY_ORB = "character/Florist/CardGeneric/card_small_orb.png";

    private static final String FLORIST_ATTACK_PORTRAIT = "character/Florist/CardGeneric/portrait_attack.png";
    private static final String FLORIST_POWER_PORTRAIT = "character/Florist/CardGeneric/portrait_power.png";
    private static final String FLORIST_SKILL_PORTRAIT = "character/Florist/CardGeneric/portrait_skill.png";
    private static final String FLORIST_CARD_ENERGY_ORB_PORTRAIT = "character/Florist/CardGeneric/card_large_orb.png";


    // Character images
    private static final String THE_FLORIST_BUTTON = "character/Florist/CharacterButton.png";
    private static final String THE_FLORIST_PORTRAIT = "character/Florist/CharacterPortrait.png";


    //PERFORM ALL INITIALIZATION
    public Agrypnos()
    {
        logger.info("INIT: Subscribing to BaseMod hooks");
        BaseMod.subscribe(this);

        logger.info("CHARACTER: Creating Florist Color");
        BaseMod.addColor(CardColorEnum.FLORIST_GREEN, FLORIST_GREEN,
                makePath(FLORIST_ATTACK_BACK), makePath(FLORIST_SKILL_BACK), makePath(FLORIST_POWER_BACK),
                makePath(FLORIST_ENERGY_ORB),
                makePath(FLORIST_ATTACK_PORTRAIT), makePath(FLORIST_SKILL_PORTRAIT), makePath(FLORIST_POWER_PORTRAIT),
                makePath(FLORIST_CARD_ENERGY_ORB_PORTRAIT), makePath(FLORIST_CARD_ENERGY_ORB));
    }

    // ===================================== CHARACTERS =====================================
    @Override
    public void receiveEditCharacters() {
        logger.info("CHARACTER: Adding the Florist");

        BaseMod.addCharacter(new TheFlorist("the Florist", CharacterEnum.THE_FLORIST),
                makePath(THE_FLORIST_BUTTON), makePath(THE_FLORIST_PORTRAIT), CharacterEnum.THE_FLORIST);
    }

    // ===================================== RELICS =====================================
    @Override
    public void receiveEditRelics() {
        logger.info("RELIC: SHARED: Added War Drum.");
        BaseMod.addRelic(new WarDrum(), RelicType.SHARED);
        logger.info("RELIC: FLORIST: Added Floriculture.");
        BaseMod.addRelicToCustomPool(new Floriculture(), CardColorEnum.FLORIST_GREEN);
        logger.info("RELIC: FLORIST: Added Flower Pot.");
        BaseMod.addRelicToCustomPool(new FlowerPot(), CardColorEnum.FLORIST_GREEN);
        logger.info("RELIC: FLORIST: Added Miniature Garden.");
        BaseMod.addRelicToCustomPool(new MiniatureGarden(), CardColorEnum.FLORIST_GREEN);
    }

    // ===================================== CARDS =====================================
    @Override
    public void receiveEditCards() {
        logger.info("CARDS: Adding all");
        // Add the cards

        //Most Stuff
        BaseMod.addCard(new BasicStrike()); //strike
        BaseMod.addCard(new BasicDefend()); //block
        BaseMod.addCard(new Thorn()); //damage + vulnerable
        BaseMod.addCard(new FloralScent()); //weak to all
        BaseMod.addCard(new Watering()); //grow all flowers
        BaseMod.addCard(new Verdancy()); //double growth
        BaseMod.addCard(new Prune()); //reset growth, draw
        BaseMod.addCard(new NaturalNutrition()); //grow plants, cost hp no energy
        BaseMod.addCard(new Tumbleweed()); //damage all enemies, exhaust
        BaseMod.addCard(new Rot()); //give poison, exhaust card
        BaseMod.addCard(new Seed()); //random flower + small amount of block
        BaseMod.addCard(new Shell()); //Defend scaling with number of flowers
        BaseMod.addCard(new MortarAndPestle()); //Heal that requires exhausting a Flower

        //Powers
        BaseMod.addCard(new Fertilize()); //Triggers growth of flowers at start of turn
        BaseMod.addCard(new Composting()); //Grants fertilize by exhausting cards
        BaseMod.addCard(new MorningSun()); //Reduces cost of un-grown flowers
        BaseMod.addCard(new Pollen()); //Poison when flowers grow
        BaseMod.addCard(new RoseGarden()); //Thorns and thorns

        //Flowers
        BaseMod.addCard(new Rose()); //basic, 2 cost damage + give thorn
        BaseMod.addCard(new Zinnia()); //common, temporary dex
        BaseMod.addCard(new Dandelion()); //common, gain block
        BaseMod.addCard(new Wolfsbane()); //common, apply poison
        BaseMod.addCard(new Sunflower()); //uncommon, give energy
        BaseMod.addCard(new CorpseFlower()); //uncommon, poison, grow on apply poison
        BaseMod.addCard(new Snapdragon()); //uncommon, deal x damage
        BaseMod.addCard(new Myosotis()); //uncommon, permanent growing damage
        BaseMod.addCard(new QueenOfTheNight()); //rare, damage reduction in hand
        BaseMod.addCard(new Narcissus()); //rare, copies a card in hand, exhausts other cards
        BaseMod.addCard(new GhostLily()); //rare, exhaust all flowers give seeds
        BaseMod.addCard(new Lotus()); //rare, increase card draw
        BaseMod.addCard(new Rue()); //rare, remove debuff/grant artifact
        BaseMod.addCard(new Camellia()); //rare, deal damage with somewhat exponential scaling

        logger.info("CARDS: Unlocking");
        // Unlock the cards
        UnlockTracker.unlockCard(BasicStrike.ID);
        UnlockTracker.unlockCard(BasicDefend.ID);
        UnlockTracker.unlockCard(Thorn.ID);
        UnlockTracker.unlockCard(FloralScent.ID);
        UnlockTracker.unlockCard(Watering.ID);
        UnlockTracker.unlockCard(Verdancy.ID);
        UnlockTracker.unlockCard(Prune.ID);
        UnlockTracker.unlockCard(NaturalNutrition.ID);
        UnlockTracker.unlockCard(Tumbleweed.ID);
        UnlockTracker.unlockCard(Rot.ID);
        UnlockTracker.unlockCard(Seed.ID);
        UnlockTracker.unlockCard(Shell.ID);
        UnlockTracker.unlockCard(MortarAndPestle.ID);

        //Powers
        UnlockTracker.unlockCard(Fertilize.ID);
        UnlockTracker.unlockCard(Composting.ID);
        UnlockTracker.unlockCard(Pollen.ID);
        UnlockTracker.unlockCard(MorningSun.ID);
        UnlockTracker.unlockCard(RoseGarden.ID);

        //Flowers
        UnlockTracker.unlockCard(Rose.ID);
        UnlockTracker.unlockCard(QueenOfTheNight.ID);
        UnlockTracker.unlockCard(GhostLily.ID);
        UnlockTracker.unlockCard(Lotus.ID);
        UnlockTracker.unlockCard(Sunflower.ID);
        UnlockTracker.unlockCard(Narcissus.ID);
        UnlockTracker.unlockCard(Zinnia.ID);
        UnlockTracker.unlockCard(CorpseFlower.ID);
        UnlockTracker.unlockCard(Dandelion.ID);
        UnlockTracker.unlockCard(Camellia.ID);
        UnlockTracker.unlockCard(Snapdragon.ID);
        UnlockTracker.unlockCard(Wolfsbane.ID);
        UnlockTracker.unlockCard(Rue.ID);
        UnlockTracker.unlockCard(Myosotis.ID);
    }

    // ===================================== STRINGS =====================================
    @Override
    public void receiveEditStrings()
    {
        logger.info("STRINGS: Adding relic strings.");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "localization/English/RelicStrings.json");

        logger.info("STRINGS: Adding card strings.");
        BaseMod.loadCustomStringsFile(CardStrings.class, "localization/English/CardStrings.json");

        logger.info("STRINGS: Adding power strings.");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "localization/English/PowerStrings.json");

        logger.info("STRINGS: Adding UI strings.");
        BaseMod.loadCustomStringsFile(UIStrings.class, "localization/English/UIStrings.json");
    }

    // ===================================== KEYWORDS =====================================
    @Override
    public void receiveEditKeywords() {
        final String[] Flower = { "Flower", "flower", "Flowers", "flowers" };
        BaseMod.addKeyword(Flower, "Cards that stay in your hand and slowly grow in power. Their growth resets when used.");

        final String[] Thorn = { "Thorn","thorn" };
        BaseMod.addKeyword(Thorn, "A 0 cost damage card that applies Vulnerable.");

        final String[] Fertilizer = { "Fertilizer", "fertilizer" };
        BaseMod.addKeyword(Fertilizer, "Triggers the growth of one random Flower at the start of your turn for each stack.");
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("Initializing Agrypnos.");
        new Agrypnos();
    }

    @Override
    public void receivePostInitialize() {

    }

    public static String createID(String partialID)
    {
        return "Agrypnos:" + partialID;
    }

    public static String makePath(String partialPath)
    {
        return "img/Agrypnos/" + partialPath;
    }
}
