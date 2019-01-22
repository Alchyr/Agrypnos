package Agrypnos;

import Agrypnos.cards.Florist.Attacks.*;
import Agrypnos.cards.Florist.Flowers.*;
import Agrypnos.cards.Florist.Attacks.FuneralWreath;
import Agrypnos.cards.Florist.Powers.*;
import Agrypnos.cards.Florist.Skills.*;
import Agrypnos.util.CardColorEnum;
import Agrypnos.util.CharacterEnum;
import Agrypnos.characters.*;
import Agrypnos.relics.*;

import Agrypnos.util.GrowthVariable;
import basemod.BaseMod;
import basemod.interfaces.*;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import com.megacrit.cardcrawl.cards.AbstractCard;
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

    public static final Color FLORIST_COLOR = CardHelper.getColor(219.0f, 197.0f, 103.0f);
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
        BaseMod.addColor(CardColorEnum.FLORIST_COLOR, FLORIST_COLOR,
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
        //logger.info("RELIC: SHARED: Added War Drum.");
        //BaseMod.addRelic(new WarDrum(), RelicType.SHARED);
        logger.info("RELIC: FLORIST: Added Floristry.");
        BaseMod.addRelicToCustomPool(new Floristry(), CardColorEnum.FLORIST_COLOR);
        logger.info("RELIC: FLORIST: Added Floriculture.");
        BaseMod.addRelicToCustomPool(new Floriculture(), CardColorEnum.FLORIST_COLOR);
        logger.info("RELIC: FLORIST: Added Flower Pot.");
        BaseMod.addRelicToCustomPool(new FlowerPot(), CardColorEnum.FLORIST_COLOR);
        logger.info("RELIC: FLORIST: Added Greenhouse.");
        BaseMod.addRelicToCustomPool(new Greenhouse(), CardColorEnum.FLORIST_COLOR);
        logger.info("RELIC: FLORIST: Added Miniature Garden.");
        BaseMod.addRelicToCustomPool(new MiniatureGarden(), CardColorEnum.FLORIST_COLOR);
    }

    // ===================================== CARDS =====================================
    @Override
    public void receiveEditCards() {
        logger.info("CARDS: Adding Growth variable");
        BaseMod.addDynamicVariable(new GrowthVariable());

        logger.info("CARDS: Adding and unlocking all");
        // Add the cards

        //Most Stuff
        AddCard(new BasicStrike()); //strike
        AddCard(new BasicDefend()); //block
        AddCard(new Trowel()); //whack and grow
        AddCard(new Till()); //whack and smack and if they have block vulnerable
        AddCard(new Rake()); //slash all and seed
        AddCard(new Replanting()); //reset a flower and now it's free
        AddCard(new Thorn()); //damage + vulnerable
        AddCard(new Bramble()); //damage + thorn
        AddCard(new FloralScent()); //weak to all
        AddCard(new FuneralWreath()); //big boy card
        AddCard(new RustyShear()); //affliction attack
        AddCard(new Watering()); //grow all flowers
        AddCard(new Rainfall()); //grow ALL flowers
        AddCard(new Sunbeam()); //get energy, grow some flowers
        AddCard(new Preserve()); //no grow for one turn, keep energy and block
        AddCard(new Weeding()); //eliminate the weak and unworthy weeds
        AddCard(new BigShovel()); //damage, get seeds
        AddCard(new ViolentBloom()); //reset flower, damage based on growth
        AddCard(new SpringArrival()); //SEEDS
        AddCard(new NaturePurity()); //exhaust not flower, get energy
        AddCard(new Verdancy()); //double growth
        AddCard(new SeedPacket()); //SEEDS
        AddCard(new Restock()); //draw flowers if no flowers
        AddCard(new EntanglingVines()); //damage + strength down temporarily
        AddCard(new CombTheEarth()); //seed in hand, seed in deck
        AddCard(new Pesticide()); //poison cycled
        AddCard(new StormShelter()); //armor, no flower growth
        AddCard(new Prune()); //reset growth, draw
        AddCard(new GeneModification()); //upgrade a flower permanently
        AddCard(new NaturalNutrition()); //grow plants, cost hp no energy
        AddCard(new Tumbleweed()); //damage all enemies, exhaust
        AddCard(new Rot()); //give poison, exhaust card
        AddCard(new Seed()); //random flower + small amount of block
        AddCard(new Shell()); //Defend scaling with number of flowers
        AddCard(new MortarAndPestle()); //Heal that requires exhausting a Flower

        //Powers
        AddCard(new Fertilize()); //Triggers growth of flowers at start of turn
        AddCard(new Composting()); //Grants fertilize by exhausting cards
        AddCard(new MorningSun()); //Reduces cost of un-grown flowers
        AddCard(new SunZenith()); //Reduces cost of most-grown flower
        AddCard(new Harvest()); //Playing grown flower gain block
        AddCard(new Winter()); //Flower no grow, flower do doubletap
        AddCard(new Summer()); //Flower mega grow
        AddCard(new Pollen()); //Poison when flowers grow
        AddCard(new RoseGarden()); //Thorns and thorns

        //Flowers
        AddCard(new Dandelion()); //basic, gain block
        AddCard(new Azalea()); //common, 1 cost damage
        AddCard(new Rose()); //common, 2 cost damage + give thorn
        AddCard(new Zinnia()); //common, temporary dex
        AddCard(new Dandelion()); //common, gain block
        AddCard(new Wolfsbane()); //common, apply poison
        AddCard(new MorningGlory()); //uncommon, decent damage, but decays instead of grow
        AddCard(new Sunflower()); //uncommon, give energy
        AddCard(new Cattail()); //uncommon, block + weak
        AddCard(new Hyacinth()); //uncommon, damage, gain strength for one attack
        AddCard(new Poppy()); //uncommon, give temporary health
        AddCard(new Carnation()); //uncommon, gain mediocre block, double if exhausted and move to discard
        AddCard(new Poinsettia()); //uncommon, exhaust cards for block
        AddCard(new CorpseFlower()); //uncommon, poison, grow on apply poison
        AddCard(new Nightshade()); //uncommon, poison, affliction
        AddCard(new Snapdragon()); //uncommon, deal x damage
        AddCard(new Orchid()); //uncommon, reduce x +growth strength
        AddCard(new Myosotis()); //uncommon, permanent growing damage
        AddCard(new Kudzu()); //rare, greedy flower that exhausts other cards
        AddCard(new QueenOfTheNight()); //rare, damage reduction in hand
        AddCard(new Narcissus()); //rare, copies a card in hand, exhausts other cards
        AddCard(new GhostLily()); //rare, exhaust all flowers give seeds
        AddCard(new Lotus()); //rare, draw cards
        AddCard(new Rue()); //rare, remove debuff/grant artifact
        AddCard(new Camellia()); //rare, deal damage with somewhat exponential scaling
    }

    private<T extends AbstractCard> void AddCard(T card)
    {
        BaseMod.addCard(card);
        UnlockTracker.unlockCard(card.cardID);
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
        BaseMod.addKeyword("Flower", Flower, "Cards that stay in your hand and slowly grow in power. Their growth resets when used.");

        final String[] Thorn = { "Thorn","thorn" };
        BaseMod.addKeyword("Thorn", Thorn, "A 0 cost damage card that applies Vulnerable.");

        final String[] Affliction = { "Affliction", "affliction" };
        BaseMod.addKeyword("Affliction", Affliction, "Negates the application of buffs.");

        final String[] Fertilizer = { "Fertilizer", "fertilizer" };
        BaseMod.addKeyword("Fertilizer", Fertilizer, "Triggers the growth of one random Flower at the start of your turn for each stack.");

        final String[] Seed = { "Seed", "seed" , "Seeds" , "seeds" };
        BaseMod.addKeyword("Seed", Seed, "#yEthereal card that grants a small amount of #yBlock and a random #yFlower when #yExhausted. #yUpgraded #ySeeds grant an #yUpgraded #yFlower.");
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
