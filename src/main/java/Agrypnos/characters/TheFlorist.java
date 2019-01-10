package Agrypnos.characters;

import java.util.*;

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
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;

import Agrypnos.Agrypnos;
import Agrypnos.util.CardColorEnum;
import Agrypnos.cards.Florist.Flowers.*;
import Agrypnos.cards.Florist.*;
import Agrypnos.relics.Floriculture;

public class TheFlorist extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(Agrypnos.class.getName());

    public static Random rnd = new Random();

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
                        "img/Agrypnos/character/Florist/Spriter/theDefaultAnimation.scml"));


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
        retVal.add(Rose.ID); //Rose is a card that does damage + vulnerable, similar to bash

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

        retVal.add(Floriculture.ID);

        UnlockTracker.markRelicAsSeen(Floriculture.ID);

        return retVal;
    }

    // Character select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("SHOVEL", 0.5f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // Character select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "BELL";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return CardColorEnum.FLORIST_GREEN;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return Agrypnos.FLORIST_GREEN;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontGreen;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return "The Florist";
    }

    //Which starting card should specific events give you?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new Rose();
    }

    // The class name as it appears next to your player name in game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return "the Florist";
    }

    // Should return a new instance of your character, sending this.name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheFlorist(this.name, this.chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return Agrypnos.FLORIST_GREEN;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return Agrypnos.FLORIST_ATTACK_HEART_PURPLE;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in damage action and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.POISON,
                AbstractGameAction.AttackEffect.POISON,
                AbstractGameAction.AttackEffect.POISON
        };
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return "You prepare to use the Heart as a seedbed.";
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return "Navigating an unlit street, you come across several hooded figures in the midst of some dark ritual. Might they want flowers? As you approach, they turn to you in eerie unison. The tallest among them bares fanged teeth and extends a long, pale hand towards you. NL ~\"Join~ ~us~ ~sister,~ ~and~ ~feel~ ~the~ ~warmth~ ~of~ ~the~ ~Spire.\"~";
    }

}