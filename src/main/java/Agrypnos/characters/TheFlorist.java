package Agrypnos.characters;

import java.util.*;

import Agrypnos.cards.Florist.Attacks.BasicStrike;
import Agrypnos.cards.Florist.Attacks.Trowel;
import Agrypnos.cards.Florist.Skills.BasicDefend;
import Agrypnos.relics.Floristry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;

import Agrypnos.Agrypnos;
import Agrypnos.util.CardColorEnum;
import Agrypnos.cards.Florist.Flowers.*;

public class TheFlorist extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(Agrypnos.class.getName());

    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 70;
    public static final int MAX_HP = 70;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    // =============== TEXTURES OF BIG ENERGY ORB ===============

    public static final String[] orbTextures = {
            "img/Agrypnos/character/Florist/orb/layer1.png",
            "img/Agrypnos/character/Florist/orb/layer2.png",
            "img/Agrypnos/character/Florist/orb/layer3.png",
            "img/Agrypnos/character/Florist/orb/layer4.png",
            "img/Agrypnos/character/Florist/orb/layer5.png",
            "img/Agrypnos/character/Florist/orb/layer6.png",
            "img/Agrypnos/character/Florist/orb/layer1d.png",
            "img/Agrypnos/character/Florist/orb/layer2d.png",
            "img/Agrypnos/character/Florist/orb/layer3d.png",
            "img/Agrypnos/character/Florist/orb/layer4d.png",
            "img/Agrypnos/character/Florist/orb/layer5d.png", };

    public TheFlorist(String name, PlayerClass setClass) {
        super(name,
                setClass,
                orbTextures,
                "img/Agrypnos/character/Florist/orb/vfx.png", null,
                new SpriterAnimation(
                        "img/Agrypnos/character/Florist/Spriter/Character.scml"));


        // =============== TEXTURES, ENERGY, LOADOUT =================

        initializeClass(null, // required call to load textures and setup energy/loadout
                "img/Agrypnos/character/Florist/shoulder.png", // campfire pose
                "img/Agrypnos/character/Florist/shoulder2.png", // another campfire pose
                "img/Agrypnos/character/Florist/corpse.png", // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager


        // =============== ANIMATIONS =================

        this.loadAnimation(
                "img/Agrypnos/character/Florist/skeleton.atlas",
                "img/Agrypnos/character/Florist/skeleton.json",
                1.0f);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());


        // =============== TEXT BUBBLE LOCATION =================

        this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
        this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values

    }











    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo("The Florist",
                "A lover of flowers. NL " + "She feels most at home among them.",
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starting Deck strings");


        retVal.add(BasicStrike.ID);
        retVal.add(BasicStrike.ID);
        retVal.add(BasicStrike.ID);
        retVal.add(BasicStrike.ID);
        retVal.add(BasicStrike.ID);

        retVal.add(BasicDefend.ID);
        retVal.add(BasicDefend.ID);
        retVal.add(BasicDefend.ID);
        retVal.add(BasicDefend.ID);

        retVal.add(Dandelion.ID); //Dandelion is basically BetterDefend
        retVal.add(Trowel.ID); //Trowel - does damage, grow a flower

        //retVal.add(Snapdragon.ID);
        //retVal.add(Sunflower.ID);

        //but more damage, less vulnerable
        //Starting deck is quite strong, but the starting relic is relatively mediocre


        //logger.info("Using test deck.");


        return retVal;
    }

    // Starting Relics
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(Floristry.ID);

        UnlockTracker.markRelicAsSeen(Floristry.ID);

        return retVal;
    }

    // Character select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("TINGSHA", 0.3f); // Sound Effect
    }

    // Character select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ORB_DARK_CHANNEL";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return CardColorEnum.FLORIST_COLOR;
    }

    @Override
    public Color getCardTrailColor() {
        return Agrypnos.FLORIST_COLOR;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontGreen;
    }

    @Override
    public String getLocalizedCharacterName() {
        return "The Florist";
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Trowel();
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return "the Florist";
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheFlorist(this.name, this.chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return Agrypnos.FLORIST_COLOR;
    }

    @Override
    public Color getSlashAttackColor() {
        return Agrypnos.FLORIST_ATTACK_HEART_PURPLE;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.POISON,
                AbstractGameAction.AttackEffect.POISON,
                AbstractGameAction.AttackEffect.POISON
        };
    }

    @Override
    public String getSpireHeartText() {
        return "You'll use its flesh as a seedbed.";
    }

    @Override
    public String getVampireText() {
        return "Navigating an unlit street, you come across several hooded figures in the midst of some dark ritual. Might they want flowers? As you approach, they turn to you in eerie unison. The tallest among them bares fanged teeth and extends a long, pale hand towards you. NL ~\"Join~ ~us~ ~sister,~ ~and~ ~feel~ ~the~ ~warmth~ ~of~ ~the~ ~Spire.\"~";
    }

}